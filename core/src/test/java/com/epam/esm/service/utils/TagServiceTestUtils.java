package com.epam.esm.service.utils;

import com.epam.esm.persistence.repository.entity.TagEntity;
import com.epam.esm.service.dto.tag.TagDTO;

import java.util.Arrays;
import java.util.List;

public class TagServiceTestUtils {
    public static List<TagEntity> getTags() {
        return Arrays.asList(
                new TagEntity().setId(1).setName("game"),
                new TagEntity().setId(2).setName("videogame"),
                new TagEntity().setId(3).setName("games"),
                new TagEntity().setId(4).setName("playstation"),
                new TagEntity().setId(5).setName("kids"),
                new TagEntity().setId(6).setName("children"),
                new TagEntity().setId(7).setName("kid"),
                new TagEntity().setId(8).setName("clothes"),
                new TagEntity().setId(9).setName("beauty"),
                new TagEntity().setId(10).setName("makeup"),
                new TagEntity().setId(11).setName("cosmetics"),
                new TagEntity().setId(12).setName("skincare")
        );
    }

    public static List<TagDTO> getTagDTOs() {
        return Arrays.asList(
                new TagDTO().setName("game"),
                new TagDTO().setName("videogame"),
                new TagDTO().setName("games"),
                new TagDTO().setName("playstation"),
                new TagDTO().setName("kids"),
                new TagDTO().setName("children"),
                new TagDTO().setName("kid"),
                new TagDTO().setName("clothes"),
                new TagDTO().setName("beauty"),
                new TagDTO().setName("makeup"),
                new TagDTO().setName("cosmetics"),
                new TagDTO().setName("skincare")
        );
    }
    public static List<TagEntity> getUpdateTags() {
        return Arrays.asList(
                new TagEntity().setId(2).setName("videogame"),
                new TagEntity().setId(3).setName("games"),
                new TagEntity().setId(4).setName("playstation"),
                new TagEntity().setId(0).setName("test")
        );
    }

    public static List<TagDTO> getUpdateTagDTOs() {
        return Arrays.asList(
                new TagDTO().setName("videogame"),
                new TagDTO().setName("games"),
                new TagDTO().setName("playstation"),
                new TagDTO().setName("test")
        );
    }

}
