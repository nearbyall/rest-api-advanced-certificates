package com.epam.esm.service;

import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.tag.TagDTO;
import com.epam.esm.service.dto.tag.TagPostDTO;

import java.util.Optional;

/**
 * Implements Service interface for business logic operations of the {@link TagDTO} class.
 */
public interface TagService extends Service<TagDTO, Integer> {

    /**
     * Gets the most widely used tag of a user with the highest cost of all orders by user id
     * @param userId user id
     * @return optional tag
     */
    Optional<TagDTO> getMostWidelyUsedTagWithHighestCostOfAllUserOrders(Integer userId);

    /**
     * Creates new tag instance
     * @param tag tag to create
     * @return optional of the created tag
     */
    Optional<TagDTO> create(TagPostDTO tag);

    /**
     * Gets tags of the specific certificate by certificate id
     * @param id certificate id
     * @param page page number
     * @param size page size
     * @return optional tag
     */
    ObjectListDTO<TagDTO> getTagsOfCertificate(Integer id, int page, int size);

    /**
     * Deletes tag by tag name
     * @param name tag name
     * @return optional of the deleted tag
     */
    Optional<TagDTO> delete(String name);

}
