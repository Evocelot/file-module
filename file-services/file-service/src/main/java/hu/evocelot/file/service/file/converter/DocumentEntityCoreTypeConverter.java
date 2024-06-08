package hu.evocelot.file.service.file.converter;

import jakarta.enterprise.context.ApplicationScoped;

import hu.evocelot.file.api.file._1_0.rest.document.DocumentEntityCoreType;
import hu.evocelot.file.model.Document;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.system.jpa.converter.IEntityConverter;

/**
 * Converter class for handling conversion between {@link Document} and {@link DocumentEntityCoreType}.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
@ApplicationScoped
public class DocumentEntityCoreTypeConverter implements IEntityConverter<Document, DocumentEntityCoreType> {

    @Override
    public DocumentEntityCoreType convert(Document entity) throws BaseException {
        DocumentEntityCoreType dto = new DocumentEntityCoreType();
        convert(dto, entity);
        return dto;
    }

    @Override
    public Document convert(DocumentEntityCoreType documentEntityCoreType) throws BaseException {
        Document entity = new Document();
        convert(entity, documentEntityCoreType);
        return entity;
    }

    @Override
    public void convert(DocumentEntityCoreType destinationDto, Document sourceEntity) throws BaseException {
        destinationDto.setName(sourceEntity.getName());
        destinationDto.setExtension(sourceEntity.getExtension());
        destinationDto.setObjectId(sourceEntity.getObjectId());
        destinationDto.setSystemId(sourceEntity.getSystemId());
        destinationDto.setNumber(sourceEntity.getNumber());
        destinationDto.setAdditionalData(sourceEntity.getAdditionalData());
    }

    @Override
    public void convert(Document destinationEntity, DocumentEntityCoreType sourceDto) throws BaseException {
        destinationEntity.setName(sourceDto.getName());
        destinationEntity.setExtension(sourceDto.getExtension());
        destinationEntity.setObjectId(sourceDto.getObjectId());
        destinationEntity.setSystemId(sourceDto.getSystemId());
        destinationEntity.setNumber(sourceDto.getNumber());
        destinationEntity.setAdditionalData(sourceDto.getAdditionalData());
    }
}
