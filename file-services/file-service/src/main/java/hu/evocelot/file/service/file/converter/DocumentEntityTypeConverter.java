package hu.evocelot.file.service.file.converter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
public class DocumentEntityTypeConverter implements IEntityConverter<Document, DocumentEntityType> {

    @Inject
    private DocumentEntityCoreTypeConverter documentEntityCoreTypeConverter;

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
        documentEntityCoreTypeConverter.convert(destinationDto, sourceEntity);

        destinationDto.setDocumentId(sourceEntity.getId());
        destinationDto.setHash(sourceEntity.getHash());
    }

    @Override
    public void convert(Document destinationEntity, DocumentEntityType sourceDto) throws BaseException {
        documentEntityCoreTypeConverter.convert(destinationEntity, sourceDto);
    }
}
