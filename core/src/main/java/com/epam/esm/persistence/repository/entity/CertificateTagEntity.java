package com.epam.esm.persistence.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name = "certificates_tags")
@IdClass(CertificateTagKey.class)
public class CertificateTagEntity {

    @Id
    @Column(name = "certificate_id")
    private Integer certificateId;

    @Id
    @Column(name = "tag_id")
    private Integer tagId;

    public Integer getCertificateId() {
        return certificateId;
    }

    public CertificateTagEntity setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
        return this;
    }

    public Integer getTagId() {
        return tagId;
    }

    public CertificateTagEntity setTagId(Integer tagId) {
        this.tagId = tagId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CertificateTagEntity that = (CertificateTagEntity) o;

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
        return  getClass().getSimpleName() + "{" +
                "certificateId=" + certificateId +
                ", tagId=" + tagId +
                '}';
    }

}
