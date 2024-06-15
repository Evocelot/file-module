package hu.evocelot.file.service.file.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import hu.evocelot.file.model.Document;
import hu.evocelot.file.service.file.helper.FileHelper;
import hu.evocelot.file.service.file.service.DocumentService;
import hu.icellmobilsoft.coffee.rest.utils.ResponseUtil;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;

/**
 * Action class for downloading the file with hash check.
 *
 * @since 0.2.0
 */
@ApplicationScoped
public class DownloadFileAction {

    @Inject
    private FileHelper fileHelper;

    @Inject
    private DocumentService documentService;

    /**
     * For downloading file with hash check.
     *
     * @param documentId
     *         - the id of the {@link Document} entity.
     * @return - the content of the file.
     * @throws BaseException
     *         - when an error occurs.
     */
    public Response downloadFile(String documentId) throws BaseException {
        if (StringUtils.isBlank(documentId)) {
            return Response.status(Status.BAD_REQUEST).entity("The documentId is null or empty!").type(MediaType.TEXT_PLAIN).build();
        }

        Document document;
        try {
            document = documentService.findById(documentId, Document.class);
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND).entity("Document not found!").type(MediaType.TEXT_PLAIN).build();
        }

        InputStream file;
        file = fileHelper.getFile(document.getId(), document.getExtension());

        try {
            byte[] fileBytes = IOUtils.toByteArray(file);
            String fileHash = fileHelper.calculateMD5Hash(new ByteArrayInputStream(fileBytes));

            if (!document.getHash().equals(fileHash)) {
                return Response.status(Status.CONFLICT)
                        .entity(MessageFormat.format("The current hash of the file [{0}] does not match the original hash of the file [{1}]!",
                                fileHash,
                                document.getHash()))
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }

            InputStream newFileStream = new ByteArrayInputStream(fileBytes);
            return ResponseUtil.getFileResponse(newFileStream,
                    fileHelper.concatFileName(document.getName(), document.getExtension()),
                    MediaType.APPLICATION_OCTET_STREAM);

        } catch (IOException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error processing file.").type(MediaType.TEXT_PLAIN).build();
        }
    }
}
