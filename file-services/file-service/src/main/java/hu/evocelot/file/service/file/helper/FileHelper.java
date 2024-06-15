package hu.evocelot.file.service.file.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import hu.evocelot.file.dto.exception.enums.FaultType;
import hu.evocelot.file.service.file.configuration.FileServiceConfiguration;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;

/**
 * Helper class for handling files on physical storage.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
@ApplicationScoped
public class FileHelper {

    private static final String MD5_DIGEST = "MD5";
    private static final int BUFFER_SIZE = 4096;

    @Inject
    private FileServiceConfiguration fileServiceConfiguration;

    /**
     * For saving the file in the physical storage.
     *
     * @param filename
     *         - the name of the file to store.
     * @param extension
     *         - the extension of the file to store.
     * @param inputStream
     *         - the file input stream.
     * @throws BaseException
     *         - when an error occurs.
     */
    public void storeFile(String filename, String extension, InputStream inputStream) throws BaseException {
        String fullPath = createFullPath(filename, extension);

        try {
            FileUtils.copyInputStreamToFile(inputStream, new File(fullPath));
        } catch (IOException e) {
            throw new BusinessException(FaultType.REST_INTERNAL_SERVER_ERROR,
                    MessageFormat.format("Cannot save file! Message: [{0}]", e.getMessage()),
                    e);
        }
    }

    /**
     * For saving the file in the physical storage with MD5 hash calculation.
     *
     * @param filename
     *         - the name of the file to store.
     * @param extension
     *         - the extension of the file to store.
     * @param inputStream
     *         - the file input stream.
     * @return - with the MD5 hash of the stream content.
     * @throws BaseException
     *         - when an error occurs.
     */
    public String storeFileWithHashCalculation(String filename, String extension, InputStream inputStream) throws BaseException {
        String fullPath = createFullPath(filename, extension);
        MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance(MD5_DIGEST);
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(FaultType.REST_INTERNAL_SERVER_ERROR, "Cannot get the MD5 MessageDigest instance.", e);
        }

        try (DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);
                FileOutputStream fileOutputStream = new FileOutputStream(fullPath)) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = digestInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new BusinessException(FaultType.REST_INTERNAL_SERVER_ERROR,
                    MessageFormat.format("Cannot save file! Message: [{0}]", e.getMessage()),
                    e);
        }

        byte[] hashBytes = messageDigest.digest();
        return Hex.encodeHexString(hashBytes);
    }

    /**
     * Deletes the file.
     *
     * @param filename
     *         - the filename.
     * @param extension
     *         - the extension.
     * @throws BusinessException
     *         - when error occurs.
     */
    public void deleteFile(String filename, String extension) throws BusinessException {
        String fullPath = createFullPath(filename, extension);

        File file = new File(fullPath);

        if (!file.delete()) {
            throw new BusinessException(FaultType.REST_INTERNAL_SERVER_ERROR, "Cannot delete the file!");
        }
    }

    /**
     * Changes the extension of a file.
     *
     * @param filename
     *         - the name of the file without extension.
     * @param originalExtension
     *         - the current extension of the file.
     * @param newExtension
     *         - the new extension to be applied.
     * @throws BusinessException
     *         - when an error occurs or the file does not exist.
     */
    public void changeExtension(String filename, String originalExtension, String newExtension) throws BusinessException {
        String originalFullPath = createFullPath(filename, originalExtension);
        String newFullPath = createFullPath(filename, newExtension);

        File originalFile = new File(originalFullPath);
        File newFile = new File(newFullPath);

        if (!originalFile.exists()) {
            throw new BusinessException(FaultType.REST_INTERNAL_SERVER_ERROR, "The file to be renamed does not exist!");
        }

        if (newFile.exists()) {
            throw new BusinessException(FaultType.REST_INTERNAL_SERVER_ERROR, "A file with the new extension already exists!");
        }

        if (!originalFile.renameTo(newFile)) {
            throw new BusinessException(FaultType.REST_INTERNAL_SERVER_ERROR, "Cannot rename the file!");
        }
    }

    /**
     * For getting the physical file in InputStream.
     *
     * @param filename
     *         - the name of the file without extension.
     * @param extension
     *         - the extension.
     * @return - with the InputStream that contains the file.
     * @throws BusinessException
     *         - when an error occurs.
     */
    public InputStream getFile(String filename, String extension) throws BusinessException {
        String fullPath = createFullPath(filename, extension);

        try {
            return new FileInputStream(fullPath);
        } catch (IOException e) {
            throw new BusinessException(FaultType.REST_INTERNAL_SERVER_ERROR, "Cannot download the file! Message: " + e.getMessage());
        }
    }

    /**
     * Concat the filename and the extension.
     *
     * @param filename
     *         - the filename.
     * @param extension
     *         - the extension.
     * @return - with filename . extension.
     */
    public String concatFileName(String filename, String extension) {
        return filename + "." + extension;
    }

    /**
     * Calculates the MD5 hash of an InputStream without draining it.
     *
     * @param inputStream
     *         - the InputStream to hash.
     * @return - with the MD5 hash as a hexadecimal string.
     * @throws IOException
     *         - if an I/O error occurs.
     */
    public String calculateMD5Hash(InputStream inputStream) throws IOException, BusinessException {
        MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance(MD5_DIGEST);
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(FaultType.REST_INTERNAL_SERVER_ERROR, "Cannot get the MD5 MessageDigest instance.", e);
        }

        try (DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest)) {
            // Read the entire InputStream to calculate the hash
            IOUtils.copy(digestInputStream, new ByteArrayOutputStream());
        }

        byte[] hashBytes = messageDigest.digest();
        return Hex.encodeHexString(hashBytes);
    }

    private String createFullPath(String filename, String extension) {
        return fileServiceConfiguration.getStorePath() + File.separatorChar + filename + "." + extension;
    }
}
