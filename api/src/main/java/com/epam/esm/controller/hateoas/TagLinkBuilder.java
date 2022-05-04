package com.epam.esm.controller.hateoas;

import com.epam.esm.controller.TagController;
import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;

/**
 * The HATEOAS link builder class for TagController controllers. Provides WebMvcLinkBuilder and Link references to the controller's methods.
 */
@Component
public class TagLinkBuilder extends LinkBuilder {

    /**
     * Gets affordances with templates for the tag.The affordances: delete tag by id, delete tag by name.
     *
     * @param id   tag id
     * @param name tag name
     * @return list of affordances
     */
    public List<Affordance> getAffordances(Integer id, String name) {
        return Arrays.asList(
                afford(methodOn(TagController.class).deleteTag(id)),
                afford((methodOn(TagController.class).deleteTag(name)))
        );
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
     * Gets pagination links for the getByCertificateId controller
     *
     * @param id         certificate id
     * @param page       page number
     * @param size       page size
     * @param totalPages total number of pages
     * @return list of pagination links (first, prev, self, next, last)
     */
    public List<Link> getPaginationLinksByCertificateId(Integer id, int page, int size, int totalPages) {
        return getLinks(
                getCertificateTags(id, 1, size),
                page > 1 ? getCertificateTags(id, page - 1, size) : null,
                getCertificateTags(id, page, size),
                page < totalPages ? getCertificateTags(id, page + 1, size) : null,
                getCertificateTags(id, totalPages, size)
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
        return linkTo(methodOn(TagController.class).getAll(page, size));
    }

    /**
     * Gets WebMvcLinkBuilder to the getById controller
     *
     * @param id tag id
     * @return WebMvcLinkBuilder to the getById controller
     */
    public WebMvcLinkBuilder getById(Integer id) {
        return linkTo(methodOn(TagController.class).getById(id));
    }

    /**
     * Gets WebMvcLinkBuilder to the getCertificateTags controller
     *
     * @param id   certificate id
     * @param page page number
     * @param size page size
     * @return WebMvcLinkBuilder to the getCertificateTags controller
     */
    public WebMvcLinkBuilder getCertificateTags(Integer id, int page, int size) {
        return linkTo(methodOn(TagController.class).getCertificateTags(id, page, size));
    }
}
