package hu.evocelot.file.service.file.configuration;

import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Configuration class for reading the configuration values from the configuration file.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
@ApplicationScoped
public class FileServiceConfiguration implements Serializable {

    /**
     * The path to store the files (physical path)
     */
    @Inject
    @ConfigProperty(name = "file.store.path")
    private String storePath;

    /**
     * For getting the store path from the configuration file.
     *
     * @return - with the path to store the files.
     */
    public String getStorePath() {
        return storePath;
    }
}
