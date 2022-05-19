package com.epam.esm.service.dto.certificate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import static com.epam.esm.service.dto.certificate.CertificateDTO.PRICE_PATTERN;

public class CertificateExclusivePatchDTO {

    @Null
    private Integer id;

    @PositiveOrZero
    @Max(365)
    private Integer duration;

    @Pattern(regexp = PRICE_PATTERN)
    private String price;

    public Integer getId() {
        return id;
    }

    public CertificateExclusivePatchDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public CertificateExclusivePatchDTO setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public CertificateExclusivePatchDTO setPrice(String price) {
        this.price = price;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CertificateExclusivePatchDTO that = (CertificateExclusivePatchDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() + " {" +
                "id=" + id +
                ", duration=" + duration +
                ", price=" + price +
                '}';
    }
}