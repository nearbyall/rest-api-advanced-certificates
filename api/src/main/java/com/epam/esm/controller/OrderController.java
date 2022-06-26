package com.epam.esm.controller;

import com.epam.esm.controller.hateoas.OrderLinkBuilder;

import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.message.ApiStatusCode;
import com.epam.esm.exception.message.MessageFactory;
import com.epam.esm.service.OrderService;

import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.order.OrderDTO;
import com.epam.esm.service.dto.order.OrderPostDTO;
import com.epam.esm.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE_STR;
import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE_SIZE_STR;
import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE;
import static com.epam.esm.controller.ControllerConstants.DEFAULT_PAGE_SIZE;
import static com.epam.esm.controller.hateoas.Rel.GET_ALL_REL;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST Controller class for operations upon the users' orders.
 * It delegates tasks to the {@link OrderService} class, uses the {@link OrderLinkBuilder} class to provide the HATEOAS
 * representation, {@link com.epam.esm.exception.message.MessageFactory} for exception messages and
 * {@link  com.epam.esm.validation.Validator} for the validation of the DTO objects.
 */
@RestController
@RequestMapping(value = "/api/V1/orders")
public class OrderController {

    private final OrderService orderService;

    private final OrderLinkBuilder linkBuilder;

    private final MessageFactory messageFactory;

    private final Validator validator;

    @Autowired
    public OrderController(OrderService orderService,
                           OrderLinkBuilder linkBuilder,
                           MessageFactory messageFactory,
                           Validator validator) {
        this.orderService = orderService;
        this.linkBuilder = linkBuilder;
        this.messageFactory = messageFactory;
        this.validator = validator;
    }

    /**
     * GET Controller for retrieving all user orders by user id in a paginated way
     *
     * @param id   user id
     * @param page page number
     * @param size page size
     * @return ResponseEntity of {@link ObjectListDTO} object with encapsulated list of {@link OrderDTO}s
     * and {@link com.epam.esm.service.dto.PageDTO}, containing all information about the current page
     * and overall count of pages
     */
    @GetMapping("/{id}")
    public ResponseEntity<ObjectListDTO<OrderDTO>> getUserOrders(
            @PathVariable Integer id,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_STR) int page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE_STR) int size) {
        ObjectListDTO<OrderDTO> orders = orderService.getByUserId(id, page, size);

        if (orders.getObjects().isEmpty()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundPlural(), ApiStatusCode.ORDER_NOT_FOUND, HttpStatus.OK
            );
        }

        int totalPages = orders.getPage().getTotalPages();
        List<Link> paginationLinks = linkBuilder.getPaginationLinks(id, page, size, totalPages);
        orders.add(paginationLinks);

        return ResponseEntity.ok(orders);
    }

    /**
     * GET Controller for retrieving order of the specific user by user id and order id
     *
     * @param userId      user id
     * @param orderId     order id
     * @return ResponseEntity of {@link OrderDTO} object with links
     */
    @GetMapping("/{userId}/{orderId}")
    public ResponseEntity<OrderDTO> getUserOrderByOrderId(@PathVariable Integer userId, @PathVariable Integer orderId) {
        Optional<OrderDTO> order = orderService.getByUserIdAndOrderId(userId, orderId);

        if (!order.isPresent()) {
            throw new ResourceNotFoundException(
                    messageFactory.getNotFoundById(userId), ApiStatusCode.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND
            );
        }

        order.get().add(
                linkTo(methodOn(this.getClass()).getUserOrderByOrderId(userId, orderId)).withSelfRel()
        );

        return ResponseEntity.ok(order.get());
    }

    /**
     * POST Controller to make an order on the certificate
     *
     * @param id            user id
     * @param orderPostDTO  DTO with desired certificate id to order and fields with validation annotations
     * @param bindingResult if the DTO has validation errors binding result will contain them
     * @return ResponseEntity of the created {@link OrderDTO} object with links
     */
    @PostMapping("/{id}")
    public ResponseEntity<OrderDTO> makeOrderOnCertificate
    (@PathVariable Integer id, @Valid @RequestBody OrderPostDTO orderPostDTO, BindingResult bindingResult, Authentication authentication) {
        validator.checkErrorsInBindingResult(bindingResult, ApiStatusCode.ORDER_BAD_REQUEST);
        Integer certificateId = orderPostDTO.getCertificateId();
        Optional<OrderDTO> order = orderService.create(id, certificateId);

        if (!order.isPresent()) {
            throw new BadRequestException(
                    messageFactory.getNotOrderedById(certificateId), ApiStatusCode.ORDER_BAD_REQUEST,
                    HttpStatus.BAD_REQUEST
            );
        }

        order.get().add(
                linkTo(methodOn(this.getClass()).getUserOrders(id, DEFAULT_PAGE, DEFAULT_PAGE_SIZE)).withRel(GET_ALL_REL)
        );

        return ResponseEntity.ok(order.get());
    }

}
