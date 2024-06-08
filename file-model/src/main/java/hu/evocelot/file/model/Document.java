package hu.evocelot.file.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;

/**
 * The Document entity.
 */
@Entity
@Table(name = "document")
public class Document extends AbstractIdentifiedAuditEntity {

    /**
     * The name of the document (filename).
     */
    @Column(name = "name", length = 250, nullable = false)
    @Size(max = 250)
    @NotNull
    private String name;

    /**
     * The extension of the document (ex: png, jpg, etc)
     */
    @Column(name = "extension", length = 10, nullable = false)
    @Size(max = 10)
    @NotNull
    private String extension;

    /**
     * The MD5 hash of the file content.
     */
    @Column(name = "hash", length = 32, nullable = false)
    @Size(max = 32)
    @NotNull
    private String hash;

    /**
     * The id of the owner object (external object).
     */
    @Column(name = "object_id", length = 100, nullable = true)
    @Size(max = 100)
    private String objectId;

    /**
     * The id of the creator system (external system).
     */
    @Column(name = "system_id", length = 100, nullable = true)
    @Size(max = 100)
    private String systemId;

    /**
     * The number of the document. Will be use for sorting the files.
     */
    @Column(name = "number", nullable = true)
    private Integer number;

    /**
     * The optional additional data.
     */
    @Column(name = "additional_data", length = 500, nullable = true)
    @Size(max = 500)
    private String additionalData;

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
     * For getting the hash.
     *
     * @return - with the hash.
     */
    public String getHash() {
        return hash;
    }

    /**
     * For setting the hash.
     *
     * @param hash
     *         - the value to store.
     */
    public void setHash(String hash) {
        this.hash = hash;
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
