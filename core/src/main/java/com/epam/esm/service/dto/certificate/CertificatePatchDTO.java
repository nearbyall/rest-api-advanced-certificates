package com.epam.esm.service.dto.certificate;

import com.epam.esm.service.CertificateService;
import com.epam.esm.service.dto.tag.TagDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

import java.util.List;

import static com.epam.esm.service.dto.certificate.CertificateDTO.PRICE_PATTERN;

/**
 * DTO class of the certificate with validation fields for the Patch method purposes.
 *
 * @see com.epam.esm.persistence.repository.entity.CertificateEntity
 * @see com.epam.esm.persistence.repository.entity.TagEntity
 * @see CertificateService#patch(CertificatePatchDTO)
 */
public class CertificatePatchDTO {

    @Null
    private Integer id;

    private String name;

    private String description;

    @Pattern(regexp = PRICE_PATTERN)
    private String price;

    @Min(0)
    private int duration;

    @Null
    private String createDate;

    @Null
    private String lastUpdateDate;

    @Valid
    private List<TagDTO> tags;

    public Integer getId() {
        return id;
    }

    public CertificatePatchDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CertificatePatchDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CertificatePatchDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public CertificatePatchDTO setPrice(String price) {
        this.price = price;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public CertificatePatchDTO setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public CertificatePatchDTO setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public CertificatePatchDTO setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public CertificatePatchDTO setTags(List<TagDTO> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CertificatePatchDTO that = (CertificatePatchDTO) o;

        if (duration != that.duration) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate != null)
            return false;
        return tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() + " {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", duration=" + duration +
                ", createDate='" + createDate + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                ", tags=" + tags +
                '}';
    }
}
