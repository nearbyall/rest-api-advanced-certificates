package com.epam.esm.controller.hateoas;

import com.epam.esm.controller.OrderController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The HATEOAS link builder class for OrderController controllers. Provides WebMvcLinkBuilder and Link references to the controller's methods.
 */
@Component
public class OrderLinkBuilder extends LinkBuilder {

    /**
     * Gets pagination links for the getAll controller
     *
     * @param id         user id
     * @param page       page number
     * @param size       page size
     * @param totalPages total number of pages
     * @return list of pagination links (first, prev, self, next, last)
     */
    public List<Link> getPaginationLinks(Integer id, int page, int size, int totalPages) {
        return getLinks(
                getAll(id, 1, size),
                page > 1 ? getAll(id, page - 1, size) : null,
                getAll(id, page, size),
                page < totalPages ? getAll(id, page + 1, size) : null,
                getAll(id, totalPages, size)
        );
    }

    /**
     * Gets WebMvcLinkBuilder to the getAll controller
     *
     * @param id   order id
     * @param page page number
     * @param size page size
     * @return WebMvcLinkBuilder to the getAll controller
     */
    public WebMvcLinkBuilder getAll(Integer id, int page, int size) {
        return linkTo(methodOn(OrderController.class).getUserOrders(id, page, size));
    }

}
