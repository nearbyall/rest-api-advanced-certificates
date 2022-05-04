package com.epam.esm.controller.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.persistence.repository.sort.SortColumn;
import com.epam.esm.persistence.repository.sort.SortOrder;
import com.epam.esm.service.dto.certificate.CertificateDTO;
import com.epam.esm.service.dto.tag.TagDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE;
import static com.epam.esm.controller.ControllerConstants.DEFAULT_SORT_ORDER;
import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE_SIZE;
import static com.epam.esm.controller.hateoas.Rel.GET_BY_TAG_NAME_REL;
import static com.epam.esm.controller.hateoas.Rel.GET_BY_NAME_REL;
import static com.epam.esm.controller.hateoas.Rel.GET_ALL_SORTED_REL;
import static com.epam.esm.controller.hateoas.Rel.EXCLUSIVE_PATCH_REL;
import static com.epam.esm.controller.hateoas.Rel.GET_ALL_REL;
import static com.epam.esm.controller.hateoas.Rel.GET_BY_ID_REL;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The HATEOAS link builder class for CertificateController controllers. Provides WebMvcLinkBuilder
 * and Link references to the controller's methods.
 */
@Component
public class CertificateLinkBuilder extends LinkBuilder {

    /**
     * Gets link to getByTagNames controller
     *
     * @param tag tag DTO
     * @return link to the getByTagNames
     */
    public Link getByTagLinks(TagDTO tag) {
        return getByMultipleTagNames(Collections.singletonList(tag.getName()), DEFAULT_PAGE, DEFAULT_PAGE_SIZE)
                .withRel(GET_BY_TAG_NAME_REL);
    }

    /**
     * Gets links for certificate possible URLs
     *
     * @param certificate certificate DTO
     * @return list of links to the certificates
     */
    public List<Link> getCertificateLinks(CertificateDTO certificate) {
        return Arrays.asList(
                getByName(certificate.getName(), DEFAULT_PAGE, DEFAULT_PAGE_SIZE).withRel(GET_BY_NAME_REL),
                exclusivePatchCertificate(certificate.getId()).withRel(EXCLUSIVE_PATCH_REL),
                getAll(DEFAULT_PAGE, DEFAULT_PAGE_SIZE).withRel(GET_ALL_REL),
                getAllSorted(SortColumn.NAME, SortOrder.valueOf(DEFAULT_SORT_ORDER.toUpperCase(Locale.ROOT)),
                        DEFAULT_PAGE, DEFAULT_PAGE_SIZE).withRel(GET_ALL_SORTED_REL),
                getAllSorted(SortColumn.DATE, SortOrder.valueOf(DEFAULT_SORT_ORDER.toUpperCase(Locale.ROOT)),
                        DEFAULT_PAGE, DEFAULT_PAGE_SIZE).withRel(GET_ALL_SORTED_REL)
        );
    }

    /**
     * Gets links with possible affordances with templates of the certificate of the provided id
     *
     * @param id  certificate id
     * @param rel method name
     * @return link with templates
     */
    public Link getLinkWithAffordances(Integer id, String rel) {
        return linkTo(CertificateController.class).slash(id).withRel(rel).andAffordances(Arrays.asList(
                afford(methodOn(CertificateController.class)
                        .deleteCertificate(id)),
                afford(methodOn(CertificateController.class)
                        .updateCertificate(id, null, null)),
                afford(methodOn(CertificateController.class)
                        .patchCertificate(id, null, null))));
    }

    /**
     * Adds to certificate DTO links to the getById and getByName controller
     *
     * @param certificate certificate DTO
     */
    public void setCertificateLinks(CertificateDTO certificate) {
        certificate.add(getById(certificate.getId()).withRel(GET_BY_ID_REL));
        certificate.add(getByName(certificate.getName(), DEFAULT_PAGE, DEFAULT_PAGE_SIZE)
                .withRel(GET_BY_NAME_REL));
    }

    /**
     * Gets pagination links for the getAll controller
     *
     * @param page       page number
     * @param size       page size
     * @param totalPages total number of pages
     * @return list of pagination links (first, prev, self, next, last)
     */
    public List<Link> getPaginationLinks(int page, int size, int totalPages) {
        return getLinks(
                getAll(1, size),
                page > 1 ? getAll(page - 1, size) : null,
                getAll(page, size),
                page < totalPages ? getAll(page + 1, size) : null,
                getAll(totalPages, size)
        );
    }

    /**
     * Gets pagination links for the getAllSorted controller
     *
     * @param sort       sort type (name, date)
     * @param order      order order (asc, desc)
     * @param page       page number
     * @param size       page size
     * @param totalPages total number of pages
     * @return list of pagination links (first, prev, self, next, last)
     */
    public List<Link> getPaginationLinksSorted(SortColumn sort, SortOrder order, int page, int size, int totalPages) {
        return getLinks(
                getAllSorted(sort, order, 1, size),
                page > 1 ? getAllSorted(sort, order, page - 1, size) : null,
                getAllSorted(sort, order, page, size),
                page < totalPages ? getAllSorted(sort, order, page + 1, size) : null,
                getAllSorted(sort, order, totalPages, size)
        );
    }

    /**
     * Gets pagination links for the getByName controller
     *
     * @param name       certificate name
     * @param page       page number
     * @param size       page size
     * @param totalPages total number of pages
     * @return list of pagination links (first, prev, self, next, last)
     */
    public List<Link> getPaginationLinksByName(String name, int page, int size, int totalPages) {
        return getLinks(
                getByName(name, 1, size),
                page > 1 ? getByName(name, page - 1, size) : null,
                getByName(name, page, size),
                page < totalPages ? getByName(name, page + 1, size) : null,
                getByName(name, totalPages, size)
        );
    }

    /**
     * Gets pagination links for the getBySearch controller
     *
     * @param search     search pattern (search by name or description)
     * @param page       page number
     * @param size       page size
     * @param totalPages total number of pages
     * @return list of pagination links (first, prev, self, next, last)
     */
    public List<Link> getPaginationLinksBySearch(String search, int page, int size, int totalPages) {
        return getLinks(getBySearch(search, 1, size),
                page > 1 ? getBySearch(search, page - 1, size) : null,
                getBySearch(search, page, size),
                page < totalPages ? getBySearch(search, page + 1, size) : null,
                getBySearch(search, totalPages, size)
        );
    }

    /**
     * Gets pagination links for the getByTagNames controller
     *
     * @param tags       list of tags for search
     * @param page       page number
     * @param size       page size
     * @param totalPages total number of pages
     * @return list of pagination links (first, prev, self, next, last)
     */
    public List<Link> getPaginationLinksByMultipleTagNames(List<String> tags, int page, int size, int totalPages) {
        return getLinks(
                getByMultipleTagNames(tags, 1, size),
                page > 1 ? getByMultipleTagNames(tags, page - 1, size) : null,
                getByMultipleTagNames(tags, page, size),
                page < totalPages ? getByMultipleTagNames(tags, page + 1, size) : null,
                getByMultipleTagNames(tags, totalPages, size)
        );
    }

    /**
     * Gets WebMvcLinkBuilder to the getAll controller
     *
     * @param page page number
     * @param size page size
     * @return WebMvcLinkBuilder to the getAll controller
     */
    public WebMvcLinkBuilder getAll(int page, int size) {
        return linkTo(methodOn(CertificateController.class).getAll(page, size));
    }

    /**
     * Gets WebMvcLinkBuilder to the getById controller
     *
     * @param id certificate id
     * @return WebMvcLinkBuilder to the getById controller
     */
    public WebMvcLinkBuilder getById(Integer id) {
        return linkTo(methodOn(CertificateController.class).getById(id));
    }

    /**
     * Gets WebMvcLinkBuilder to the getAllSorted controller
     *
     * @param sort  sort type (name, date)
     * @param order sort order (asc, desc)
     * @param page  page number
     * @param size  page size
     * @return WebMvcLinkBuilder to the getAllSorted controller
     */
    public WebMvcLinkBuilder getAllSorted(SortColumn sort, SortOrder order, int page, int size) {
        return linkTo(methodOn(CertificateController.class).getAllSorted(sort, order, page, size));
    }

    /**
     * Gets WebMvcLinkBuilder to the getByName controller
     *
     * @param name certificate name
     * @param page page number
     * @param size page size
     * @return WebMvcLinkBuilder to the getByName controller
     */
    public WebMvcLinkBuilder getByName(String name, int page, int size) {
        return linkTo(methodOn(CertificateController.class).getByName(name, page, size));
    }

    /**
     * Gets WebMvcLinkBuilder to the exclusivePatchCertificate controller
     *
     * @param id certificate id
     * @return WebMvcLinkBuilder to the exclusivePatchCertificate controller
     */
    public WebMvcLinkBuilder exclusivePatchCertificate(Integer id) {
        return linkTo(methodOn(CertificateController.class)
                .exclusivePatchCertificate(id, null, null));
    }

    /**
     * Gets WebMvcLinkBuilder to the getByMultipleTagNames controller
     *
     * @param tags list of tag names
     * @param page page number
     * @param size page size
     * @return WebMvcLinkBuilder to the getByMultipleTagNames controller
     */
    public WebMvcLinkBuilder getByMultipleTagNames(List<String> tags, int page, int size) {
        return linkTo(methodOn(CertificateController.class).getByMultipleTagNames(tags, page, size));
    }

    /**
     * Gets WebMvcLinkBuilder to the getBySearch controller
     *
     * @param search search pattern (by name or description)
     * @param page   page number
     * @param size   page size
     * @return WebMvcLinkBuilder to the getBySearch controller
     */
    public WebMvcLinkBuilder getBySearch(String search, int page, int size) {
        return linkTo(methodOn(CertificateController.class).searchByNameOrDescription(search, page, size));
    }
}
