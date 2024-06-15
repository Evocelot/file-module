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
     * The description of the document id param.
     */
    public static final String DOCUMENT_ID_PARAM_DESCRIPTION = "The unique identifier of the document.";

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

    /**
     * The summary of the update document details endpoint.
     */
    public static final String UPDATE_DOCUMENT_DETAILS_SUMMARY = "Update document details";

    /**
     * The description of the update document details endpoint.
     */
    public static final String UPDATE_DOCUMENT_DETAILS_DESCRIPTION = "Endpoint for updating the details of the document. ";

    /**
     * The summary of the delete document endpoint.
     */
    public static final String DELETE_DOCUMENT_SUMMARY = "Delete file";

    /**
     * The description of the delete document endpoint.
     */
    public static final String DELETE_DOCUMENT_DESCRIPTION = "Endpoint for deleting the file and the details of the document.";

    /**
     * The summary of the get file details endpoint.
     */
    public static final String GET_DOCUMENT_DETAILS_SUMMARY = "Get file details";

    /**
     * The description of the get file details endpoint.
     */
    public static final String GET_DOCUMENT_DETAILS_DESCRIPTION = "Endpoint for getting the details of the file.";

    /**
     * The summary of the download file endpoint.
     */
    public static final String DOWNLOAD_FILE_SUMMARY = "Download file";

    /**
     * The description of the download file endpoint.
     */
    public static final String DOWNLOAD_FILE_DESCRIPTION = "Endpoint for downloading the physical file.";
}
