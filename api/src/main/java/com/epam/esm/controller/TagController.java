package com.epam.esm.controller;

import com.epam.esm.controller.hateoas.CertificateLinkBuilder;
import com.epam.esm.controller.hateoas.TagLinkBuilder;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.message.ApiStatusCode;
import com.epam.esm.exception.message.MessageFactory;
import com.epam.esm.service.TagService;

import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.tag.TagDTO;
import com.epam.esm.service.dto.tag.TagPostDTO;
import com.epam.esm.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE_STR;
import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE_SIZE_STR;
import static com.epam.esm.controller.hateoas.Rel.GET_BY_TAG_NAME_REL;

/**
 * REST Controller class for operations upon the certificate tags.
 * It delegates tasks to the {@link TagService} class, uses the {@link TagLinkBuilder} class to provide
 * the HATEOAS representation, {@link com.epam.esm.exception.message.MessageFactory} for exception messages
 * and {@link  Validator} for the validation of the DTO objects.
 */
@RestController
@RequestMapping(value = "/api/V1")
public class TagController {

    private final TagService tagService;

    private final TagLinkBuilder tagLinkBuilder;

    private final CertificateLinkBuilder certificateLinkBuilder;

    private final MessageFactory messageFactory;

    private final Validator validator;

    @Autowired
    public TagController(TagService tagService,
                         TagLinkBuilder tagLinkBuilder,
                         CertificateLinkBuilder certificateLinkBuilder,
                         MessageFactory messageFactory,
                         Validator validator) {
        this.tagService = tagService;
        this.tagLinkBuilder = tagLinkBuilder;
        this.certificateLinkBuilder = certificateLinkBuilder;
        this.messageFactory = messageFactory;
        this.validator = validator;
    }

    /**
     * GET Controller for retrieving all tags in a paginated way
     *
     * @param page page number
     * @param size page size
     * @return ResponseEntity of {@link ObjectListDTO} object with encapsulated list of {@link TagDTO}s
     * and {@link com.epam.esm.service.dto.PageDTO}, containing all information about the current page and overall count of
     * pages
     */
    @GetMapping(value = "/tags")
    public ResponseEntity<ObjectListDTO<TagDTO>> getAll(
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_STR) int page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE_STR) int size) {
        ObjectListDTO<TagDTO> tags = tagService.getAll(page, size);

        if (tags.getObjects().isEmpty()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundPlural(), ApiStatusCode.TAG_NOT_FOUND, HttpStatus.OK
            );
        }

        tags.getObjects().forEach(tag -> tag.add(certificateLinkBuilder.getByTagLinks(tag).withRel(GET_BY_TAG_NAME_REL)));

        int totalPages = tags.getPage().getTotalPages();
        List<Link> paginationLinks = tagLinkBuilder.getPaginationLinks(page, size, totalPages);
        tags.add(paginationLinks);

        return ResponseEntity.ok(tags);
    }

    /**
     * GET Controller for retrieving tag by id
     *
     * @param id tag id
     * @return ResponseEntity of {@link TagDTO} object with links
     */
    @GetMapping(value = "/tags/{id}")
    public ResponseEntity<TagDTO> getById(@PathVariable Integer id) {
        Optional<TagDTO> tag = tagService.getById(id);

        if (!tag.isPresent()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundById(id), ApiStatusCode.TAG_NOT_FOUND, HttpStatus.NOT_FOUND
            );
        }

        tag.get().add(
                tagLinkBuilder.getById(id).withSelfRel().andAffordances(tagLinkBuilder.getAffordances(id, tag.get().getName())),
                certificateLinkBuilder.getByTagLinks(tag.get()).withRel(GET_BY_TAG_NAME_REL));

        return ResponseEntity.ok(tag.get());
    }

    /**
     * GET Controller for retrieving tags of the specific certificate by certificate id in a paginated way
     *
     * @param id   certificate id
     * @param page page number
     * @param size page size
     * @return ResponseEntity of {@link ObjectListDTO} object with encapsulated list of {@link TagDTO}s
     * and {@link com.epam.esm.service.dto.PageDTO}, containing all information about the current page and overall
     * count of pages
     */
    @GetMapping(value = "/certificates/{id}/tags")
    public ResponseEntity<ObjectListDTO<TagDTO>> getCertificateTags(
            @PathVariable Integer id,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_STR) int page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE_STR) int size) {
        ObjectListDTO<TagDTO> tags = tagService.getTagsOfCertificate(id, page, size);

        if (tags.getObjects().isEmpty()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundPluralById(id), ApiStatusCode.TAG_NOT_FOUND, HttpStatus.NOT_FOUND
            );
        }

        tags.getObjects().forEach(tag -> tag.add(certificateLinkBuilder.getByTagLinks(tag).withRel(GET_BY_TAG_NAME_REL)));

        int totalPages = tags.getPage().getTotalPages();
        List<Link> paginationLinks = tagLinkBuilder.getPaginationLinksByCertificateId(id, page, size, totalPages);
        tags.add(paginationLinks);

        return ResponseEntity.ok(tags);
    }

    /**
     * GET Controller for retrieving the most widely used tag of a user with the highest cost of all orders by user id
     *
     * @param id user id
     * @return ResponseEntity of {@link TagDTO} object
     */
    @GetMapping(value = "/users/{id}/toptag")
    public ResponseEntity<TagDTO> getMostWidelyUsedTagWithHighestCostOfAllUserOrders
    (@PathVariable Integer id) {
        Optional<TagDTO> tag = tagService.getMostWidelyUsedTagWithHighestCostOfAllUserOrders(id);

        if (!tag.isPresent()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundById(id), ApiStatusCode.TAG_NOT_FOUND, HttpStatus.NOT_FOUND
            );
        }

        return ResponseEntity.ok(tag.get());
    }

    /**
     * POST Controller for adding new tag
     *
     * @param tag           tag DTO with fields validation
     * @param bindingResult if the DTO has validation errors binding result will contain them
     * @return ResponseEntity of {@link TagDTO} object with links
     */
    @PostMapping(value = "/tags")
    public ResponseEntity<TagDTO> addNewTag(@Valid @RequestBody TagPostDTO tag, BindingResult bindingResult) {
        validator.checkErrorsInBindingResult(bindingResult, ApiStatusCode.TAG_BAD_REQUEST);

        Optional<TagDTO> createdTag = tagService.create(tag);
        if (!createdTag.isPresent()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotAdded(), ApiStatusCode.TAG_BAD_REQUEST, HttpStatus.BAD_REQUEST
            );
        }

        createdTag.get().add(certificateLinkBuilder.getByTagLinks(createdTag.get()).withRel(GET_BY_TAG_NAME_REL));

        return ResponseEntity.ok(createdTag.get());
    }

    /**
     * DELETE Controller for tag deletion by id
     *
     * @param id tag id
     * @return ResponseEntity of the deleted {@link TagDTO} object
     */
    @DeleteMapping(value = "/tags/{id}")
    public ResponseEntity<TagDTO> deleteTag(@PathVariable Integer id) {
        Optional<TagDTO> deletedTag = tagService.delete(id);

        if (!deletedTag.isPresent()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotDeletedById(id), ApiStatusCode.TAG_BAD_REQUEST, HttpStatus.BAD_REQUEST
            );
        }

        return ResponseEntity.ok(deletedTag.get());
    }

    /**
     * DELETE Controller for tag deletion by name
     *
     * @param name tag name
     * @return ResponseEntity of the deleted {@link TagDTO} object
     */
    @DeleteMapping(value = "/tags", params = {"name"})
    public ResponseEntity<TagDTO> deleteTag(@RequestParam String name) {
        Optional<TagDTO> deletedTag = tagService.delete(name);

        if (!deletedTag.isPresent()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotDeletedByName(name), ApiStatusCode.TAG_BAD_REQUEST, HttpStatus.BAD_REQUEST
            );
        }

        return ResponseEntity.ok(deletedTag.get());
    }

}
