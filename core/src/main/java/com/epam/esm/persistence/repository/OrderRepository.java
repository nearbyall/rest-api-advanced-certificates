package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.repository.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

/**
 * Extends {@link CrudRepository} interface for CRUD operations for {@link OrderEntity} entity.
 */
public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {

    /**
     * Gets user's orders by user id
     *
     * @param id     user id
     * @param limit  limit of entities
     * @param offset offset for the entities
     * @return list of orders
     */
    List<OrderEntity> getByUserId(Integer id, int limit, int offset);

    /**
     * Gets user's order by user id and order id
     *
     * @param userId  user id
     * @param orderId order is
     * @return optional order of the user
     */
    Optional<OrderEntity> getByUserIdAndOrderId(Integer userId, Integer orderId);

    /**
     * Gets order by certificate id if it is has been ordered
     *
     * @param id certificate id
     * @return optional order
     */
    Optional<OrderEntity> getOrderByCertificateId(Integer id);

    /**
     * Gets the number of all existing orders
     *
     * @return order count
     */
    Integer getCount();

    /**
     * Gets the number of the user's orders by user id
     * @param id user id
     * @return order count
     */
    Integer getCountByUserId(Integer id);

}
