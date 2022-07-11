package com.epam.esm.service;

import com.epam.esm.persistence.repository.sort.SortColumn;
import com.epam.esm.persistence.repository.sort.SortOrder;
import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.certificate.CertificateDTO;
import com.epam.esm.service.dto.certificate.CertificateExclusivePatchDTO;
import com.epam.esm.service.dto.certificate.CertificateNewDTO;
import com.epam.esm.service.dto.certificate.CertificatePatchDTO;

import java.util.List;
import java.util.Optional;

/**
 * Extends Service interface for business logic operations of the {@link CertificateDTO} class.
 */
public interface CertificateService extends Service<CertificateDTO, Integer> {

    /**
     * Creates new certificate instance
     *
     * @param certificate certificate to create
     * @return optional of created certificate
     */
    Optional<CertificateDTO> create(CertificateNewDTO certificate);

    /**
     * Gets all sorted certificates DTOs in a paginated way.
     *
     * @param sort  sort table (name or date)
     * @param order sort order (asc or desc)
     * @param page  page number
     * @param size  page size
     * @return list of sorted certificates DTOs with page information
     */
    ObjectListDTO<CertificateDTO> getAllSorted(SortColumn sort, SortOrder order, int page, int size);

    /**
     * Gets all existing objects with the provided certificate name.
     *
     * @param name certificate name
     * @return list of certificate DTOs with page information
     */
    ObjectListDTO<CertificateDTO> getByName(String name, int page, int size);

    /**
     * Gets certificates by several tag names in a paginated way.
     *
     * @param tags list of tag names
     * @param page page number
     * @param size page size
     * @return list of certificate DTOs with page information
     */
    ObjectListDTO<CertificateDTO> getByTagNames(List<String> tags, int page, int size);

    /**
     * Updates certificate and its tags.
     * Can delete tags corresponding to that certificate.
     * All fields of the CertificateDTO must be provided.
     *
     * @param certificate certificate DTO with fields that needed to be updated
     * @return optional DTO of the updated certificate
     */
    Optional<CertificateDTO> update(CertificateNewDTO certificate);

    /**
     * Patch method for certificate and its tags.
     * Fields could be provided partially.
     *
     * @param certificate certificate DTO with patch fields
     * @return optional DTO of the updated certificate
     */
    Optional<CertificateDTO> patch(CertificatePatchDTO certificate);

    /**
     * Gets all existing certificates by searching by name or description.
     *
     * @param search search pattern
     * @param page   page number
     * @param size   page size
     * @return list of resulted certificates DTOs with page information
     */
    ObjectListDTO<CertificateDTO> searchByNameOrDescription(String search, int page, int size);

    /**
     * Exclusive certificate patch, updates duration or price
     *
     * @param certificate certificate DTO with 1 field to patch
     * @return optional DTO of the updated certificate
     */
    Optional<CertificateDTO> exclusivePatch(CertificateExclusivePatchDTO certificate);

}
