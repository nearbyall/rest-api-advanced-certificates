package com.epam.esm.service.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO class of the tag
 *
 * @see com.epam.esm.persistence.repository.entity.TagEntity
 */
public class TagDTO extends RepresentationModel<TagDTO> {

    @NotNull
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public TagDTO setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TagDTO tagDTO = (TagDTO) o;

        return name != null ? name.equals(tagDTO.name) : tagDTO.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() +
                "{" +
                "name='" + name + '\'' +
                '}';
    }

}
