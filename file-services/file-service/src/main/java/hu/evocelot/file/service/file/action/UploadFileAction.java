package hu.evocelot.file.service.file.action;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Objects;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.evocelot.file.api.file._1_0.rest.document.DocumentResponse;
import hu.evocelot.file.api.rest.jee10.dto.UploadFileRequest;
import hu.evocelot.file.common.system.rest.action.BaseAction;
import hu.evocelot.file.dto.exception.enums.FaultType;
import hu.evocelot.file.model.Document;
import hu.evocelot.file.service.file.converter.DocumentEntityTypeConverter;
import hu.evocelot.file.service.file.helper.FileHelper;
import hu.evocelot.file.service.file.service.DocumentService;
import hu.icellmobilsoft.coffee.jpa.helper.TransactionHelper;
import hu.icellmobilsoft.coffee.model.base.generator.EntityIdGenerator;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import hu.icellmobilsoft.coffee.se.api.exception.DtoConversionException;

/**
 * Action class for handling the file uploading.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
@ApplicationScoped
public class UploadFileAction extends BaseAction {

    @Inject
    private DocumentEntityTypeConverter documentEntityTypeConverter;

    @Inject
    private DocumentService documentService;

    @Inject
    private TransactionHelper transactionHelper;

    @Inject
    private FileHelper fileHelper;

    /**
     * For uploading files. The file will be stored on the physical storage.
     *
     * @param uploadFileRequest
     *         - the multipart request that contains the file and the metadata of the file.
     * @return - with {@link DocumentResponse} that contains information about the metadata of the created document.
     * @throws BaseException
     *         - when an error occurs.
     */
    public DocumentResponse uploadFile(UploadFileRequest uploadFileRequest) throws BaseException {
        if (Objects.isNull(uploadFileRequest)) {
            throw new DtoConversionException("The uploadFileRequest is null!");
        }

        InputStream fileInputStream = uploadFileRequest.getFileInputStream();
        String name = uploadFileRequest.getName();
        String extension = uploadFileRequest.getExtension();

        if (Objects.isNull(fileInputStream)) {
            throw new DtoConversionException("The fileInputStream is null!");
        } else if (StringUtils.isBlank(name)) {
            throw new DtoConversionException("The name is null!");
        } else if (StringUtils.isBlank(extension)) {
            throw new DtoConversionException("The extension is null!");
        }

        String documentId = EntityIdGenerator.generateId();

        String md5Hash = fileHelper.storeFileWithHashCalculation(documentId, extension, fileInputStream);

        Document document = convertToDocument(uploadFileRequest);
        document.setId(documentId);
        document.setHash(md5Hash);

        Document finalEntity = document;

        try {
            document = transactionHelper.executeWithTransaction(() -> documentService.save(finalEntity));
        } catch (BaseException e) {
            fileHelper.deleteFile(documentId, extension);
            throw new BusinessException(FaultType.REST_INTERNAL_SERVER_ERROR,
                    MessageFormat.format("File upload failed. Message: [{0}]", e.getMessage()));
        }

        DocumentResponse response = new DocumentResponse();
        response.setDocument(documentEntityTypeConverter.convert(document));
        handleSuccessResultType(response);

        return response;
    }

    private Document convertToDocument(UploadFileRequest uploadFileRequest) {
        Document document = new Document();

        document.setName(uploadFileRequest.getName());
        document.setExtension(uploadFileRequest.getExtension());
        document.setObjectId(uploadFileRequest.getObjectId());
        document.setSystemId(uploadFileRequest.getSystemId());
        document.setNumber(uploadFileRequest.getNumber());
        document.setAdditionalData(uploadFileRequest.getAdditionalData());

        return document;
    }
}
