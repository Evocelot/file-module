package hu.evocelot.file.service.file.converter;

import jakarta.enterprise.context.ApplicationScoped;

import hu.evocelot.file.api.file._1_0.rest.document.DocumentEntityType;
import hu.evocelot.file.model.Document;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.system.jpa.converter.IEntityConverter;

/**
 * Converter class for handling conversion between {@link Document} and {@link DocumentEntityType}.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
@ApplicationScoped
public class DocumentTypeConverter implements IEntityConverter<Document, DocumentEntityType> {

    @Override
    public DocumentEntityType convert(Document entity) throws BaseException {
        DocumentEntityType dto = new DocumentEntityType();
        convert(dto, entity);
        return dto;
    }

    @Override
    public Document convert(DocumentEntityType documentEntityType) throws BaseException {
        Document entity = new Document();
        convert(entity, documentEntityType);
        return entity;
    }

    @Override
    public void convert(DocumentEntityType destinationDto, Document sourceEntity) throws BaseException {
        destinationDto.setDocumentId(sourceEntity.getId());
        destinationDto.setName(sourceEntity.getName());
        destinationDto.setExtension(sourceEntity.getExtension());
        destinationDto.setHash(sourceEntity.getHash());
        destinationDto.setObjectId(sourceEntity.getObjectId());
        destinationDto.setSystemId(sourceEntity.getSystemId());
        destinationDto.setNumber(sourceEntity.getNumber());
        destinationDto.setAdditionalData(sourceEntity.getAdditionalData());
    }

    @Override
    public void convert(Document destinationEntity, DocumentEntityType sourceDto) throws BaseException {
        destinationEntity.setName(sourceDto.getName());
        destinationEntity.setExtension(sourceDto.getExtension());
        destinationEntity.setObjectId(sourceDto.getObjectId());
        destinationEntity.setSystemId(sourceDto.getSystemId());
        destinationEntity.setNumber(sourceDto.getNumber());
        destinationEntity.setAdditionalData(sourceDto.getAdditionalData());
    }
}
