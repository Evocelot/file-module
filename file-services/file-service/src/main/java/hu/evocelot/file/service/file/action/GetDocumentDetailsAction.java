package hu.evocelot.file.service.file.action;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.evocelot.file.api.file._1_0.rest.document.DocumentResponse;
import hu.evocelot.file.common.system.rest.action.BaseAction;
import hu.evocelot.file.model.Document;
import hu.evocelot.file.service.file.converter.DocumentEntityTypeConverter;
import hu.evocelot.file.service.file.service.DocumentService;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;

/**
 * Action class for getting the details of the file.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
@ApplicationScoped
public class GetDocumentDetailsAction extends BaseAction {

    @Inject
    private DocumentEntityTypeConverter documentEntityTypeConverter;

    @Inject
    private DocumentService documentService;

    /**
     * For getting the details of the file.
     *
     * @param documentId
     *         - the id of the {@link Document} entity to get.
     * @return - with the {@link DocumentResponse} that contains the details of the file.
     * @throws BaseException
     *         - when an error occurs.
     */
    public DocumentResponse getDocumentDetails(String documentId) throws BaseException {
        if (StringUtils.isBlank(documentId)) {
            throw new InvalidParameterException("The documentId is null!");
        }

        Document document = documentService.findById(documentId, Document.class);

        DocumentResponse response = new DocumentResponse();
        response.setDocument(documentEntityTypeConverter.convert(document));

        handleSuccessResultType(response);

        return response;
    }
}
