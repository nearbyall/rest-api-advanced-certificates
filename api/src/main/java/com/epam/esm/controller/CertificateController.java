package com.epam.esm.controller;

import com.epam.esm.controller.hateoas.CertificateLinkBuilder;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.message.ApiStatusCode;
import com.epam.esm.exception.message.MessageFactory;
import com.epam.esm.persistence.repository.sort.SortColumn;
import com.epam.esm.persistence.repository.sort.SortOrder;
import com.epam.esm.service.CertificateService;

import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.certificate.CertificateDTO;
import com.epam.esm.service.dto.certificate.CertificateExclusivePatchDTO;
import com.epam.esm.service.dto.certificate.CertificateNewDTO;
import com.epam.esm.service.dto.certificate.CertificatePatchDTO;
import com.epam.esm.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE_STR;
import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE_SIZE_STR;
import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE;
import static com.epam.esm.controller.ControllerConstants.DEFAULT_SORT_ORDER;
import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE_SIZE;
import static com.epam.esm.controller.hateoas.Rel.SELF_REL;
import static com.epam.esm.controller.hateoas.Rel.GET_ALL_SORTED_REL;
import static com.epam.esm.controller.hateoas.Rel.GET_ALL_REL;
import static com.epam.esm.controller.hateoas.Rel.GET_BY_ID_REL;

/**
 * REST Controller class for operations upon the certificates.
 * It delegates tasks to the {@link CertificateService} class, uses the
 * {@link CertificateLinkBuilder} class to provide the HATEOAS representation,
 * {@link com.epam.esm.exception.message.MessageFactory} for exception messages and {@link com.epam.esm.validation.Validator}
 * for the validation of the DTO objects.
 */
@RestController
@RequestMapping(value = "/api/V1/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    private final CertificateLinkBuilder linkBuilder;

    private final MessageFactory messageFactory;

    private final Validator validator;

    @Autowired
    public CertificateController(CertificateService certificateService,
                                 CertificateLinkBuilder linkBuilder,
                                 MessageFactory messageFactory,
                                 Validator validator) {
        this.certificateService = certificateService;
        this.linkBuilder = linkBuilder;
        this.messageFactory = messageFactory;
        this.validator = validator;
    }

    /**
     * GET Controller for retrieving certificate by id
     *
     * @param id certificate id
     * @return ResponseEntity of {@link CertificateDTO} object with links
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificateDTO> getById(@PathVariable Integer id) {
        Optional<CertificateDTO> certificate = certificateService.getById(id);

        if (!certificate.isPresent()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundById(id), ApiStatusCode.CERTIFICATE_NOT_FOUND, HttpStatus.NOT_FOUND
            );
        }

        setCertificateLinksSingular(certificate.get(), SELF_REL);

        return ResponseEntity.ok(certificate.get());
    }

    /**
     * GET Controller for retrieving all certificates in a paginated way
     *
     * @param page page number
     * @param size page size
     * @return ResponseEntity of {@link ObjectListDTO} object with encapsulated list of {@link CertificateDTO}s and
     * {@link com.epam.esm.service.dto.PageDTO}, containing all information about the current page and overall count of pages
     */
    @GetMapping
    public ResponseEntity<ObjectListDTO<CertificateDTO>> getAll(
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_STR) int page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE_STR) int size) {
        ObjectListDTO<CertificateDTO> certificates = certificateService.getAll(page, size);

        if (certificates.getObjects().isEmpty()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundPlural(), ApiStatusCode.CERTIFICATE_NOT_FOUND, HttpStatus.OK
            );
        }

        setCertificatesLinks(certificates);

        int totalPages = certificates.getPage().getTotalPages();
        List<Link> paginationLinks = linkBuilder.getPaginationLinks(page, size, totalPages);
        certificates.add(paginationLinks);

        certificates.add(Arrays.asList(
                linkBuilder.getAllSorted(SortColumn.NAME, SortOrder.valueOf(DEFAULT_SORT_ORDER.toUpperCase(Locale.ROOT)),
                        DEFAULT_PAGE, DEFAULT_PAGE_SIZE).withRel(GET_ALL_SORTED_REL),
                linkBuilder.getAllSorted(SortColumn.DATE, SortOrder.valueOf(DEFAULT_SORT_ORDER.toUpperCase(Locale.ROOT)),
                        DEFAULT_PAGE, DEFAULT_PAGE_SIZE).withRel(GET_ALL_SORTED_REL))
        );

        return ResponseEntity.ok(certificates);
    }

    /**
     * GET Controller for retrieving all certificates sorted in a paginated way
     *
     * @param sort  sort type (name, date)
     * @param order order type (asc, desc)
     * @param page  page number
     * @param size  page size
     * @return ResponseEntity of {@link ObjectListDTO} object with encapsulated list of {@link CertificateDTO}s
     * and {@link com.epam.esm.service.dto.PageDTO}, containing all information about the current page and overall count of
     * pages
     */
    @GetMapping(params = {"sort"})
    public ResponseEntity<ObjectListDTO<CertificateDTO>> getAllSorted(
            @RequestParam SortColumn sort,
            @RequestParam(required = false, defaultValue = DEFAULT_SORT_ORDER) SortOrder order,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_STR) int page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE_STR) int size) {
        ObjectListDTO<CertificateDTO> certificates = certificateService.getAllSorted(sort, order, page, size);

        if (certificates.getObjects().isEmpty()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundPlural(), ApiStatusCode.CERTIFICATE_NOT_FOUND, HttpStatus.OK
            );
        }

        setCertificatesLinks(certificates);

        int totalPages = certificates.getPage().getTotalPages();
        List<Link> paginationLinks = linkBuilder.getPaginationLinksSorted(sort, order, page, size, totalPages);
        certificates.add(paginationLinks);
        certificates.add(linkBuilder.getAll(DEFAULT_PAGE, DEFAULT_PAGE_SIZE).withRel(GET_ALL_REL));

        return ResponseEntity.ok(certificates);
    }

    /**
     * GET Controller for retrieving all certificates by provided name in a paginated way
     *
     * @param name certificate name
     * @param page page number
     * @param size page size
     * @return ResponseEntity of {@link ObjectListDTO} object with encapsulated list of {@link CertificateDTO} and
     * {@link com.epam.esm.service.dto.PageDTO}, containing all information about the current page and overall count of pages
     */
    @GetMapping(params = {"name"})
    public ResponseEntity<ObjectListDTO<CertificateDTO>> getByName(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_STR) int page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE_STR) int size) {
        ObjectListDTO<CertificateDTO> certificates = certificateService.getByName(name, page, size);

        if (certificates.getObjects().isEmpty()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundByName(name), ApiStatusCode.CERTIFICATE_NOT_FOUND, HttpStatus.NOT_FOUND
            );
        }

        setCertificatesLinks(certificates);

        int totalPages = certificates.getPage().getTotalPages();
        List<Link> paginationLinks = linkBuilder.getPaginationLinksByName(name, page, size, totalPages);
        certificates.add(paginationLinks);

        setGetAllLinks(certificates);

        return ResponseEntity.ok(certificates);
    }

    /**
     * GET Controller for retrieving all certificates by provided tag names in a paginated way
     *
     * @param tags list of tag names
     * @param page page number
     * @param size page size
     * @return ResponseEntity of {@link ObjectListDTO} object with encapsulated list of {@link CertificateDTO}s
     * and {@link com.epam.esm.service.dto.PageDTO}, containing all information about the current page and overall count of
     * pages
     */
    @GetMapping(params = {"tags"})
    public ResponseEntity<ObjectListDTO<CertificateDTO>> getByMultipleTagNames(
            @RequestParam List<String> tags,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_STR) int page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE_STR) int size) {

        if (tags.isEmpty()) {
            throw new ResourceNotFoundException(
                    messageFactory.getInvalidData(), ApiStatusCode.CERTIFICATE_BAD_REQUEST, HttpStatus.BAD_REQUEST
            );
        }
        ObjectListDTO<CertificateDTO> certificates = certificateService.getByTagNames(tags, page, size);

        if (certificates.getObjects().isEmpty()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundByTag(tags.toString()),
                    ApiStatusCode.CERTIFICATE_NOT_FOUND,
                    HttpStatus.NOT_FOUND
            );
        }

        setCertificatesLinks(certificates);

        int totalPages = certificates.getPage().getTotalPages();
        List<Link> paginationLinks = linkBuilder.getPaginationLinksByMultipleTagNames(tags, page, size, totalPages);
        certificates.add(paginationLinks);

        setGetAllLinks(certificates);

        return ResponseEntity.ok(certificates);
    }

    /**
     * GET Controller for retrieving all certificates by provided name or description search in a paginated way
     *
     * @param search search pattern
     * @param page   page number
     * @param size   page size
     * @return ResponseEntity of {@link ObjectListDTO} object with encapsulated list of {@link CertificateDTO}s
     * and {@link com.epam.esm.service.dto.PageDTO}, containing all information about the current page and overall count of
     * pages
     */
    @GetMapping(params = {"search"})
    public ResponseEntity<ObjectListDTO<CertificateDTO>> searchByNameOrDescription(
            @RequestParam String search,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_STR) int page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE_STR) int size) {
        ObjectListDTO<CertificateDTO> certificates = certificateService.searchByNameOrDescription(search, page, size);

        if (certificates.getObjects().isEmpty()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundBySearch(search), ApiStatusCode.CERTIFICATE_NOT_FOUND, HttpStatus.OK
            );
        }

        setCertificatesLinks(certificates);

        int totalPages = certificates.getPage().getTotalPages();
        List<Link> paginationLinks = linkBuilder.getPaginationLinksBySearch(search, page, size, totalPages);
        certificates.add(paginationLinks);

        setGetAllLinks(certificates);

        return ResponseEntity.ok(certificates);
    }

    /**
     * POST Controller for adding new certificate
     *
     * @param postCertificate     DTO with validation for fields
     * @param bindingResult       if the DTO has validation errors binding result will contain them
     * @return ResponseEntity of {@link CertificateDTO} object with links
     */
    @PostMapping
    public ResponseEntity<CertificateDTO> addNewCertificate
    (@Valid @RequestBody CertificateNewDTO postCertificate, BindingResult bindingResult) {
        validator.checkErrorsInBindingResult(bindingResult, ApiStatusCode.CERTIFICATE_BAD_REQUEST);
        Optional<CertificateDTO> certificate = certificateService.create(postCertificate);

        if (!certificate.isPresent()) {
            throw new BadRequestException(
                    messageFactory.getNotAdded(), ApiStatusCode.CERTIFICATE_BAD_REQUEST, HttpStatus.BAD_REQUEST
            );
        }

        setCertificateLinksSingular(certificate.get(), GET_BY_ID_REL);

        return ResponseEntity.ok(certificate.get());
    }

    /**
     * PUT Controller for update the certificate by id
     *
     * @param id                    certificate id
     * @param updateCertificate     DTO with all fields containing new fields to update and validation annotations
     * @param bindingResult         if the DTO has validation errors binding result will contain them
     * @return ResponseEntity of {@link CertificateDTO} object with links
     */
    @PutMapping("/{id}")
    public ResponseEntity<CertificateDTO> updateCertificate
    (@PathVariable Integer id, @Valid @RequestBody CertificateNewDTO updateCertificate, BindingResult bindingResult) {
        validator.checkErrorsInBindingResult(bindingResult, ApiStatusCode.CERTIFICATE_BAD_REQUEST);

        Optional<CertificateDTO> certificate = certificateService.update(updateCertificate.setId(id));

        if (!certificate.isPresent()) {
            throw new BadRequestException(
                    messageFactory.getNotUpdatedById(id), ApiStatusCode.CERTIFICATE_BAD_REQUEST, HttpStatus.BAD_REQUEST
            );
        }

        setCertificateLinksSingular(certificate.get(), GET_BY_ID_REL);

        return ResponseEntity.ok(certificate.get());
    }

    /**
     * PATCH Controller to patch certificate with any quantity of fields
     *
     * @param id              certificate id
     * @param certificate     DTO with validation for fields
     * @param bindingResult   if the DTO has validation errors binding result will contain them
     * @return ResponseEntity of {@link CertificateDTO} object with links
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CertificateDTO> patchCertificate
    (@PathVariable Integer id,
     @Valid @RequestBody CertificatePatchDTO certificate, BindingResult bindingResult) {
        validator.checkErrorsInBindingResult(bindingResult, ApiStatusCode.CERTIFICATE_BAD_REQUEST);

        Optional<CertificateDTO> certificateDTO = certificateService.patch(certificate.setId(id));

        if (!certificateDTO.isPresent()) {
            throw new BadRequestException(
                    messageFactory.getNotUpdatedById(id), ApiStatusCode.CERTIFICATE_BAD_REQUEST, HttpStatus.BAD_REQUEST
            );
        }

        setCertificateLinksSingular(certificateDTO.get(), GET_BY_ID_REL);

        return ResponseEntity.ok(certificateDTO.get());
    }

    /**
     * PATCH Controller for exclusive patch, only duration or price
     *
     * @param id                   certificate id
     * @param patchCertificate     DTO with single field
     * @param bindingResult        if the DTO has validation errors binding result will contain them
     * @return ResponseEntity of {@link CertificateDTO} object with links
     */
    @PatchMapping("/{id}/patch")
    public ResponseEntity<CertificateDTO> exclusivePatchCertificate
    (@PathVariable Integer id,
     @Valid @RequestBody CertificateExclusivePatchDTO patchCertificate, BindingResult bindingResult) {
        validator.checkExclusivePatchHavingOneArgument(patchCertificate);
        validator.checkErrorsInBindingResult(bindingResult, ApiStatusCode.CERTIFICATE_BAD_REQUEST);

        Optional<CertificateDTO> certificate = certificateService.exclusivePatch(patchCertificate.setId(id));

        if (!certificate.isPresent()) {
            throw new BadRequestException(
                    messageFactory.getNotUpdatedById(id), ApiStatusCode.CERTIFICATE_BAD_REQUEST, HttpStatus.BAD_REQUEST
            );
        }

        setCertificateLinksSingular(certificate.get(), GET_BY_ID_REL);

        return ResponseEntity.ok(certificate.get());
    }

    /**
     * DELETE Controller for certificate deletion by id
     *
     * @param id certificate id
     * @return ResponseEntity of the deleted {@link CertificateDTO}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CertificateDTO> deleteCertificate(@PathVariable Integer id) {
        Optional<CertificateDTO> certificateDTO = certificateService.delete(id);

        if (!certificateDTO.isPresent()) {
            throw new BadRequestException(
                    messageFactory.getNotDeletedById(id), ApiStatusCode.CERTIFICATE_NOT_FOUND, HttpStatus.NOT_FOUND
            );
        }

        return ResponseEntity.ok(certificateDTO.get());
    }

    private void setCertificateLinksSingular(CertificateDTO certificateDTO, String rel) {
        certificateDTO.add(linkBuilder.getCertificateLinks(certificateDTO));
        certificateDTO.add(linkBuilder.getLinkWithAffordances(certificateDTO.getId(), rel));
        certificateDTO.getTags().forEach(tag -> tag.add(linkBuilder.getByTagLinks(tag)));
    }

    private void setCertificatesLinks(ObjectListDTO<CertificateDTO> certificates) {
        certificates.getObjects().forEach(linkBuilder::setCertificateLinks);
        certificates.getObjects().forEach(gc -> gc.getTags().forEach(tag -> tag.add(linkBuilder.getByTagLinks(tag))));
    }

    private void setGetAllLinks(ObjectListDTO<CertificateDTO> certificates) {
        certificates.add(Arrays.asList(
                linkBuilder.getAll(DEFAULT_PAGE, DEFAULT_PAGE_SIZE).withRel(GET_ALL_REL),
                linkBuilder.getAllSorted(SortColumn.NAME, SortOrder.valueOf(DEFAULT_SORT_ORDER.toUpperCase(Locale.ROOT)),
                        DEFAULT_PAGE, DEFAULT_PAGE_SIZE).withRel(GET_ALL_SORTED_REL),
                linkBuilder.getAllSorted(SortColumn.DATE, SortOrder.valueOf(DEFAULT_SORT_ORDER.toUpperCase(Locale.ROOT)),
                        DEFAULT_PAGE, DEFAULT_PAGE_SIZE).withRel(GET_ALL_SORTED_REL))
        );
    }

}
