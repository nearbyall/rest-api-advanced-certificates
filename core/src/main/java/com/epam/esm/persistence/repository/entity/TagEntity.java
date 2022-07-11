package com.epam.esm.persistence.repository.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tags")
public class TagEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<CertificateEntity> certificates;

    public Integer getId() {
        return id;
    }

    public TagEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TagEntity setName(String name) {
        this.name = name;
        return this;
    }

    public List<CertificateEntity> getCertificates() {
        return certificates;
    }

    public TagEntity setCertificates(List<CertificateEntity> certificates) {
        this.certificates = certificates;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagEntity tagEntity = (TagEntity) o;

        if (id != null ? !id.equals(tagEntity.id) : tagEntity.id != null) return false;
        if (name != null ? !name.equals(tagEntity.name) : tagEntity.name != null) return false;
        return certificates != null ? certificates.equals(tagEntity.certificates) : tagEntity.certificates == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (certificates != null ? certificates.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
