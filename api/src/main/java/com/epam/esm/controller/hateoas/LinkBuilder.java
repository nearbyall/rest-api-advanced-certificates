package com.epam.esm.controller.hateoas;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.controller.hateoas.Rel.FIRST_REL;
import static com.epam.esm.controller.hateoas.Rel.PREV_REL;
import static com.epam.esm.controller.hateoas.Rel.NEXT_REL;
import static com.epam.esm.controller.hateoas.Rel.LAST_REL;

/**
 * The HATEOAS link builder class. Provides WebMvcLinkBuilder references of the controllers methods.
 */
public abstract class LinkBuilder {
    /**
     * Pagination links builder
     *
     * @param firstBuilder first page link
     * @param prevBuilder  previous page link, if there is no previous page returns null
     * @param selfBuilder  self page link
     * @param nextBuilder  next page link, if there is no next page returns null
     * @param lastBuilder  last page link
     * @return list of page links
     */
    protected List<Link> getLinks(WebMvcLinkBuilder firstBuilder,
                                  WebMvcLinkBuilder prevBuilder,
                                  WebMvcLinkBuilder selfBuilder,
                                  WebMvcLinkBuilder nextBuilder,
                                  WebMvcLinkBuilder lastBuilder) {
        Link first = firstBuilder.withRel(FIRST_REL);
        Link prev = prevBuilder != null ? prevBuilder.withRel(PREV_REL) : null;
        Link self = selfBuilder.withSelfRel();
        Link next = nextBuilder != null ? nextBuilder.withRel(NEXT_REL) : null;
        Link last = lastBuilder.withRel(LAST_REL);

        return Stream.of(first, prev, self, next, last)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
