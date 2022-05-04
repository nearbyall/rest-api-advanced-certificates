package com.epam.esm.service.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TagPostDTO {

    @NotNull
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public TagPostDTO setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagPostDTO that = (TagPostDTO) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() +
                "{" +
                "name='" + name + '\'' +
                '}';
    }
}
