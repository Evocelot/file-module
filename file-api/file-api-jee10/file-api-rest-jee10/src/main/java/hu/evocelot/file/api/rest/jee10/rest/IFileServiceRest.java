package hu.evocelot.file.api.rest.jee10.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import hu.evocelot.file.api.common.path.FileRestPath;
import hu.evocelot.file.api.common.restinformation.FileRestInformation;
import hu.evocelot.file.api.file._1_0.rest.document.DocumentRequest;
import hu.evocelot.file.api.file._1_0.rest.document.DocumentResponse;
import hu.evocelot.file.api.rest.jee10.dto.UploadFileRequest;
import hu.evocelot.file.dto.constant.XsdConstants;
import hu.icellmobilsoft.coffee.cdi.annotation.xml.ValidateXML;
import hu.icellmobilsoft.coffee.dto.url.BaseServicePath;
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

    /**
     * HTTP PUT for updating document entity.
     *
     * @param documentId
     *         - the id of the entity to update.
     * @param documentRequest
     *         - the request DTO that contains information about the updated data.
     * @return - with {@link DocumentResponse} that contains the updated details.
     * @throws BaseException
     *         - when an error occurs.
     */
    @PUT
    @Operation(summary = FileRestInformation.UPDATE_DOCUMENT_DETAILS_SUMMARY, description = FileRestInformation.UPDATE_DOCUMENT_DETAILS_DESCRIPTION)
    @Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Path(FileRestPath.ID)
    DocumentResponse updateFileDetails(@Parameter(required = true, description = FileRestInformation.DOCUMENT_ID_PARAM_DESCRIPTION) @PathParam(
            BaseServicePath.PARAM_ID) String documentId, @ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) DocumentRequest documentRequest)
            throws BaseException;
}
