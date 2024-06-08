package hu.evocelot.file.service.file.helper;

import java.io.File;
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

    private String createFullPath(String filename, String extension) {
        return fileServiceConfiguration.getStorePath() + File.separatorChar + filename + "." + extension;
    }
}
