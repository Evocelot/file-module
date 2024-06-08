package hu.evocelot.file.api.common.path;

import hu.icellmobilsoft.coffee.dto.url.BaseServicePath;

/**
 * Path class for defining the paths for the file service.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
public class FileRestPath extends BaseServicePath {
    /**
     * The {@value } sub-path.
     */
    public static final String FILE_SERVICE = "/file-service";

    /**
     * The base path of the file service.
     */
    public static final String FILE_SERVICE_BASE_PATH = REST + FILE_SERVICE;

    /**
     * The {@value } sub-path.
     */
    public static final String UPLOAD = "/upload";
}
