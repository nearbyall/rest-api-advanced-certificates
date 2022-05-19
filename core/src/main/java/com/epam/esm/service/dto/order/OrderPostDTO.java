package com.epam.esm.service.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderPostDTO {

    @Min(1)
    @NotNull
    private Integer certificateId;

    public Integer getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPostDTO that = (OrderPostDTO) o;

        return certificateId != null ? certificateId.equals(that.certificateId) : that.certificateId == null;
    }

    @Override
    public int hashCode() {
        return certificateId != null ? certificateId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() + " {" +
                "certificateId=" + certificateId +
                '}';
    }
}
