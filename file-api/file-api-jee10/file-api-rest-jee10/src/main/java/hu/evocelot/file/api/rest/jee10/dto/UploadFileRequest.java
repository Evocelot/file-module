package hu.evocelot.file.api.rest.jee10.dto;

import java.io.InputStream;
import java.io.Serializable;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequestType;

/**
 * Request class for uploading the files.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
public class UploadFileRequest extends BaseRequestType implements Serializable {

    @FormParam("fileInputStream")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream fileInputStream;

    @FormParam("name")
    @PartType(MediaType.TEXT_PLAIN)
    private String name;

    @FormParam("extension")
    @PartType(MediaType.TEXT_PLAIN)
    private String extension;

    @FormParam("objectId")
    @PartType(MediaType.TEXT_PLAIN)
    private String objectId;

    @FormParam("systemId")
    @PartType(MediaType.TEXT_PLAIN)
    private String systemId;

    @FormParam("number")
    @PartType(MediaType.TEXT_PLAIN)
    private Integer number;

    @FormParam("additionalData")
    @PartType(MediaType.TEXT_PLAIN)
    private String additionalData;

    /**
     * For getting the fileInputStream. The fileInputStream stores the file stream.
     *
     * @return - with the fileInputStream.
     */
    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    /**
     * For setting the fileInputStream. The fileInputStream stores the file stream.
     *
     * @param fileInputStream
     *         - the value to store.
     */
    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    /**
     * For getting the name. This name will be used when downloading the file.
     *
     * @return - with the name.
     */
    public String getName() {
        return name;
    }

    /**
     * For setting the name. This name will be used when downloading the file.
     *
     * @param name
     *         - the value to store.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * For getting the extension.
     *
     * @return - with the extension.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * For setting the extension.
     *
     * @param extension
     *         - the value to store.
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * For getting the objectId. The object id is the external owner of the file.
     *
     * @return - with the objectId.
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * For setting the objectId. The object id is the external owner of the file.
     *
     * @param objectId
     *         - the value to store.
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * For getting the systemId. This id represents the file creator system.
     *
     * @return - with the systemId.
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * For setting the systemId. This id represents the file creator system.
     *
     * @param systemId
     *         - the value to store.
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * For getting the number. This value will be used for sorting the files for the owner.
     *
     * @return - with the number.
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * For setting the number. This value will be used for sorting the files for the owner.
     *
     * @param number
     *         - the value to store.
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * For getting the additionalData. This field represents the additional fields.
     *
     * @return - with the additionalData.
     */
    public String getAdditionalData() {
        return additionalData;
    }

    /**
     * For setting the additionalData. This field represents the additional fields.
     *
     * @param additionalData
     *         - the value to store.
     */
    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }
}
