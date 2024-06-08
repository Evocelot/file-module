package hu.evocelot.file.service.file.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import hu.evocelot.file.api.file._1_0.rest.document.DocumentRequest;
import hu.evocelot.file.api.file._1_0.rest.document.DocumentResponse;
import hu.evocelot.file.api.rest.jee10.dto.UploadFileRequest;
import hu.evocelot.file.api.rest.jee10.rest.IFileServiceRest;
import hu.evocelot.file.common.system.rest.rest.BaseRestService;
import hu.evocelot.file.service.file.action.UpdateDocumentDetailsAction;
import hu.evocelot.file.service.file.action.UploadFileAction;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;

/**
 * Rest class that implements the {@link IFileServiceRest} interface.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
@ApplicationScoped
public class FileServiceRest extends BaseRestService implements IFileServiceRest {

    @Inject
    private UploadFileAction uploadFileAction;

    @Inject
    private UpdateDocumentDetailsAction updateDocumentDetailsAction;

    @Override
    public DocumentResponse uploadFile(UploadFileRequest uploadFileRequest) throws BaseException {
        return wrapPathParam1(uploadFileAction::uploadFile, uploadFileRequest, "uploadFile", "uploadFileRequest");
    }

    @Override
    public DocumentResponse updateFileDetails(String documentId, DocumentRequest documentRequest) throws BaseException {
        return wrapPathParam2(updateDocumentDetailsAction::updateFileDetails,
                documentId,
                documentRequest,
                "updateFileDetails",
                "documentId",
                "documentRequest");
    }
}
