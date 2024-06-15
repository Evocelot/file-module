package hu.evocelot.file.service.file.action;

import java.util.Objects;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.evocelot.file.api.file._1_0.rest.document.DocumentEntityCoreType;
import hu.evocelot.file.api.file._1_0.rest.document.DocumentRequest;
import hu.evocelot.file.api.file._1_0.rest.document.DocumentResponse;
import hu.evocelot.file.common.system.rest.action.BaseAction;
import hu.evocelot.file.model.Document;
import hu.evocelot.file.service.file.converter.DocumentEntityCoreTypeConverter;
import hu.evocelot.file.service.file.converter.DocumentEntityTypeConverter;
import hu.evocelot.file.service.file.helper.FileHelper;
import hu.evocelot.file.service.file.service.DocumentService;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.jpa.helper.TransactionHelper;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;

/**
 * Action class for updating the {@link Document} entity.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
@ApplicationScoped
public class UpdateDocumentDetailsAction extends BaseAction {

    @Inject
    private DocumentService documentService;

    @Inject
    private DocumentEntityCoreTypeConverter documentEntityCoreTypeConverter;

    @Inject
    private DocumentEntityTypeConverter documentEntityTypeConverter;

    @Inject
    private TransactionHelper transactionHelper;

    @Inject
    private FileHelper fileHelper;

    /**
     * For updating the {@link Document} entity.
     *
     * @param documentId
     *         - the id of the {@link Document} to update.
     * @param documentRequest
     *         - the request that contains the details of the updated entity.
     * @return - with the {@link DocumentResponse} that contains the details of the updated data.
     * @throws BaseException
     *         - when an error occurs.
     */
    public DocumentResponse updateFileDetails(String documentId, DocumentRequest documentRequest) throws BaseException {
        if (StringUtils.isBlank(documentId)) {
            throw new InvalidParameterException("The documentId is null!");
        } else if (Objects.isNull(documentRequest)) {
            throw new InvalidParameterException("The documentRequest is null!");
        }

        Document document = documentService.findById(documentId, Document.class);

        DocumentEntityCoreType requestDocumentDetails = documentRequest.getDocument();
        if (!document.getExtension().equals(requestDocumentDetails.getExtension())) {
            fileHelper.changeExtension(document.getId(), document.getExtension(), requestDocumentDetails.getExtension());
        }

        documentEntityCoreTypeConverter.convert(document, documentRequest.getDocument());

        Document finalEntity = document;
        document = transactionHelper.executeWithTransaction(() -> documentService.save(finalEntity));

        DocumentResponse documentResponse = new DocumentResponse();
        documentResponse.setDocument(documentEntityTypeConverter.convert(document));
        handleSuccessResultType(documentResponse, documentRequest);

        return documentResponse;
    }
}
