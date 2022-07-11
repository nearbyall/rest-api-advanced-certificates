package com.epam.esm.service;

import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.order.OrderDTO;

import java.util.Optional;

/**
 * Extends Service interface for business logic operations of the {@link OrderDTO} class.
 */
public interface OrderService extends Service<OrderDTO, Integer> {

    /**
     * Gets user orders by user id in a paginated way
     *
     * @param id   user id
     * @param page page number
     * @param size page size
     * @return list of orders of the specific user with page information
     */
    ObjectListDTO<OrderDTO> getByUserId(Integer id, int page, int size);

    /**
     * Gets order of the specific user by user id and order id
     *
     * @param userId  user id
     * @param orderId order id
     * @return optional order
     */
    Optional<OrderDTO> getByUserIdAndOrderId(Integer userId, Integer orderId);

    /**
     * Creates new order of the certificate for the user
     *
     * @param userId            user id
     * @param certificateId certificate id to order
     * @return optional created order
     */
    Optional<OrderDTO> create(Integer userId, Integer certificateId);
}
