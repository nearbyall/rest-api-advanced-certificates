package com.epam.esm.persistence.repository.entity;

import java.io.Serializable;

public class CertificateTagKey implements Serializable {

    private Integer certificateId;

    private Integer tagId;

    public Integer getСertificateId() {
        return certificateId;
    }

    public void setСertificateId(Integer сertificateId) {
        this.tagId = сertificateId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CertificateTagKey that = (CertificateTagKey) o;

        if (certificateId != null ? !certificateId.equals(that.certificateId) : that.certificateId != null)
            return false;
        return tagId != null ? tagId.equals(that.tagId) : that.tagId == null;
    }

    @Override
    public int hashCode() {
        int result = certificateId != null ? certificateId.hashCode() : 0;
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() +
                "{" +
                "certificateId=" + certificateId +
                ", tagId=" + tagId +
                '}';
    }

}
