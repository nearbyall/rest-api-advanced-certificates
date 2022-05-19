package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.repository.entity.CertificateEntity;
import com.epam.esm.persistence.repository.sort.SortColumn;
import com.epam.esm.persistence.repository.sort.SortOrder;

import java.util.List;
import java.util.Optional;

/**
 * Extends {@link CrudRepository} interface for CRUD operations for {@link CertificateEntity} entity.
 */
public interface CertificateRepository extends CrudRepository<CertificateEntity, Integer> {

    /**
     * Gets all existing entities with the provided name.
     *
     * @param name   certificate name
     * @param limit  limit of entities
     * @param offset offset for the entities
     * @return list of certificates with the provided name
     */
    List<CertificateEntity> getByName(String name, int limit, int offset);

    /**
     * Gets certificate by certificate object. Removes the given entity from the persistence context and flushes it for the new retrieval by id
     *
     * @param certificate Certificate object
     * @return optional object retrieved by id
     */
    Optional<CertificateEntity> getById(CertificateEntity certificate);

    /**
     * Gets all existing entities with the provided tag name.
     *
     * @param tag    tag name
     * @param limit  limit of entities
     * @param offset offset for the entities
     * @return list of certificates with the provided tag name
     */
    List<CertificateEntity> getByTagName(String tag, int limit, int offset);

    /**
     * Gets certificates having provided tags
     *
     * @param tags   list of tags
     * @param limit  limit of entities
     * @param offset offset for the entities
     * @return list of certificates with the provided tag names
     */
    List<CertificateEntity> getByMultipleTagNames(List<String> tags, int limit, int offset);

    /**
     * Gets all existing entities of certificates sorted.
     *
     * @param sort   sort table (name or date)
     * @param order  sort order (asc or desc)
     * @param limit  limit of entities
     * @param offset offset for the entities
     * @return list of sorted certificates
     */
    List<CertificateEntity> getAllSorted(SortColumn sort, SortOrder order, int limit, int offset);

    /**
     * Gets all existing certificates searching by name or description
     *
     * @param search search pattern
     * @param limit  limit of entities
     * @param offset offset for the entities
     * @return list of resulted certificates
     */
    List<CertificateEntity> searchByNameOrDescription(String search, int limit, int offset);

    /**
     * Gets the number of all existing certificates
     *
     * @return certificates count
     */
    Integer getCount();

    /**
     * Gets the number of all existing certificates with provided name
     *
     * @param name certificate name
     * @return certificates count by provided name
     */
    Integer getByNameCount(String name);

    /**
     * Gets the number of all existing certificates searching by name or description
     *
     * @param search search pattern
     * @return certificates count by search
     */
    Integer getSearchCount(String search);

    /**
     * Gets the number of all existing certificates with provided tag name
     *
     * @param tag tag name
     * @return certificates count by tag name
     */
    Integer getByTagCount(String tag);

    /**
     * Gets the number of all existing certificates with provided tag names
     *
     * @param tag list of tag names
     * @return certificates count by tag names
     */
    Integer getByTagsCount(List<String> tag);

}
