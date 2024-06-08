package hu.evocelot.file.api.rest.jee10.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import hu.evocelot.file.api.common.path.FileRestPath;
import hu.evocelot.file.api.common.restinformation.FileRestInformation;
import hu.evocelot.file.api.file._1_0.rest.document.DocumentResponse;
import hu.evocelot.file.api.rest.jee10.dto.UploadFileRequest;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;

/**
 * Interface for the REST endpoints.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
@Tag(name = FileRestInformation.TAG, description = FileRestInformation.DESCRIPTION)
@Path(FileRestPath.FILE_SERVICE_BASE_PATH)
public interface IFileServiceRest {

    /**
     * HTTP POST method for uploading files and stores in the physical storage.
     *
     * @param uploadFileRequest
     *         - the multipart request that contains the information about the file to upload and the file itself.
     * @throws BaseException
     *         - when an error occurs.
     */
    @POST
    @Operation(summary = FileRestInformation.UPLOAD_FILE_SUMMARY, description = FileRestInformation.UPLOAD_FILE_DESCRIPTION)
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA })
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Path(FileRestPath.UPLOAD)
    DocumentResponse uploadFile(@MultipartForm UploadFileRequest uploadFileRequest) throws BaseException;
}
