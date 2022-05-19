package com.epam.esm.service.mapper;

import com.epam.esm.persistence.repository.entity.TagEntity;
import com.epam.esm.service.dto.tag.TagDTO;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public TagDTO toTagDTO(TagEntity tag) {
        return new TagDTO().setName(tag.getName());
    }

    public TagEntity toTag(TagDTO tagDTO) {
        return new TagEntity().setName(tagDTO.getName());
    }
}
