package com.epam.esm.service.dto.certificate;

import com.epam.esm.service.dto.tag.TagDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

import static com.epam.esm.service.dto.certificate.CertificateDTO.PRICE_PATTERN;

public class CertificateNewDTO {

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

    public Integer getId() {
        return id;
    }

    public CertificateNewDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CertificateNewDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CertificateNewDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public CertificateNewDTO setPrice(String price) {
        this.price = price;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public CertificateNewDTO setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public CertificateNewDTO setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public CertificateNewDTO setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public CertificateNewDTO setTags(List<TagDTO> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CertificateNewDTO that = (CertificateNewDTO) o;

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

