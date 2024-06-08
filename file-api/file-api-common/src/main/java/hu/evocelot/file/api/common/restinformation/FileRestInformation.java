package hu.evocelot.file.api.common.restinformation;

/**
 * Class for storing information about the file rest.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
public class FileRestInformation {
    private FileRestInformation() {
        throw new IllegalStateException();
    }

    /**
     * The TAG.
     */
    public static final String TAG = "File service";

    /**
     * The service description.
     */
    public static final String DESCRIPTION = "File service for handling the file operations.";

    /**
     * The summary of the upload endpoint.
     */
    public static final String UPLOAD_FILE_SUMMARY = "Upload file";

    /**
     * The description of the upload endpoint.
     */
    public static final String UPLOAD_FILE_DESCRIPTION = "Endpoint for uploading files. " //
            + "It stores the file in physical storage and creates the necessary entries " //
            + "in the database. Possible additional error types: [DtoConversionException]";
}
