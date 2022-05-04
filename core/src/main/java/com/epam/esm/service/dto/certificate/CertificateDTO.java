package com.epam.esm.service.dto.certificate;

import com.epam.esm.service.dto.tag.TagDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

/**
 * DTO class of the certificate that contains Tag domain objects.
 *
 * @see com.epam.esm.persistence.repository.entity.CertificateEntity
 * @see com.epam.esm.persistence.repository.entity.TagEntity
 */
public class CertificateDTO extends RepresentationModel<CertificateDTO> {

    @Null
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Pattern(regexp = PRICE_PATTERN)
    private String price;

    @Min(1)
    private int duration;

    @Null
    private String createDate;

    @Null
    private String lastUpdateDate;

    @NotNull
    @Valid
    private List<TagDTO> tags;

    static final String PRICE_PATTERN = "[0-9]{1,4}.[0-9]{2}";

    public Integer getId() {
        return id;
    }

    public CertificateDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CertificateDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CertificateDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public CertificateDTO setPrice(String price) {
        this.price = price;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public CertificateDTO setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public CertificateDTO setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public CertificateDTO setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public CertificateDTO setTags(List<TagDTO> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CertificateDTO that = (CertificateDTO) o;

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
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
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
