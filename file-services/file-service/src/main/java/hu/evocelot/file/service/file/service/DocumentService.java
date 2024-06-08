package hu.evocelot.file.service.file.service;

import jakarta.enterprise.context.ApplicationScoped;

import hu.evocelot.file.common.system.jpa.service.BaseService;
import hu.evocelot.file.model.Document;

/**
 * Service class for handling {@link Document}s.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
@ApplicationScoped
public class DocumentService extends BaseService<Document> {

}
