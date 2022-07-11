package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.repository.entity.TagEntity;

import java.util.List;
import java.util.Optional;

/**
 * Extends {@link CrudRepository} interface for CRUD operations for {@link TagEntity} entity.
 */
public interface TagRepository extends CrudRepository<TagEntity, Integer> {

    /**
     * Gets tag entity with the provided name.
     *
     * @param name tag name
     * @return optional tag
     */
    Optional<TagEntity> getByName(String name);

    /**
     * Gets tags of a specific certificate in a paginated way.
     *
     * @param certificateId     certificate id
     * @param limit             limit of entities
     * @param offset            offset for the entities
     * @return list of tags
     */
    List<TagEntity> getCertificateTagsById(Integer certificateId, int limit, int offset);

    /**
     * Adds tag to the certificate
     *
     * @param certificateId     certificate id
     * @param tagId             tag id
     * @return the number of rows affected
     */
    Integer addCertificateTag(Integer certificateId, Integer tagId);

    /**
     * Deletes relation of the certificate with tag
     *
     * @param certificateId     certificate id
     * @param tagId             tag id
     * @return the number of rows affected
     */
    Integer deleteCertificateTag(Integer certificateId, Integer tagId);

    /**
     * Gets tag entity that belongs to the specific certificate and has a provided name
     *
     * @param certificateId certificate id
     * @param tagName           tag name
     * @return optional tag
     */
    Optional<TagEntity> getCertificateTagByTagName(Integer certificateId, String tagName);

    /**
     * Gets the most widely used tag of a user with the highest cost of all orders by user id
     *
     * @param id user id
     * @return optional tag
     */
    Optional<TagEntity> getMostWidelyUsedTagWithHighestCostOfAllUserOrders(Integer id);

    /**
     * Deletes a tag by name
     *
     * @param name tag name
     * @return number of rows affected
     */
    Integer delete(String name);

    /**
     * Gets the number of all existing tags
     *
     * @return tag count
     */
    Integer getCount();

    /**
     * Gets the number of all tags of the certificate by certificate id
     *
     * @param id certificate id
     * @return tag count
     */
    Integer getCountByCertificateId(Integer id);

}
