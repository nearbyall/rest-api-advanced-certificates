package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderRepositoryJpaImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Integer> create(OrderEntity order) {
        entityManager.persist(order);

        Integer id = order.getId();
        return id != 0
                ? Optional.of(id)
                : Optional.empty();
    }

    @Override
    public List<OrderEntity> getAll(int limit, int offset) {
        return entityManager
                .createQuery("SELECT o FROM OrderEntity o", OrderEntity.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<OrderEntity> getById(Integer id) {
        List<OrderEntity> orders = entityManager
                .createQuery("SELECT o FROM OrderEntity o WHERE o.id = :id", OrderEntity.class)
                .setParameter("id", id)
                .getResultList();

        return orders.isEmpty()
                ? Optional.empty()
                : Optional.of(orders.get(0));
    }

    @Override
    public Integer update(OrderEntity order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer delete(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrderEntity> getByUserId(Integer id, int limit, int offset) {
        return entityManager
                .createQuery("SELECT o FROM OrderEntity o WHERE o.user.id = :id", OrderEntity.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public Optional<OrderEntity> getByUserIdAndOrderId(Integer userId, Integer orderId) {
        List<OrderEntity> orders = entityManager
                .createQuery("SELECT o FROM OrderEntity o WHERE o.user.id = :userId AND o.id = :orderId", OrderEntity.class)
                .setParameter("userId", userId)
                .setParameter("orderId", orderId)
                .getResultList();

        return orders.isEmpty()
                ? Optional.empty()
                : Optional.of(orders.get(0));
    }

    @Override
    public Optional<OrderEntity> getOrderByCertificateId(Integer id) {
        List<OrderEntity> orders = entityManager
                .createQuery("SELECT o FROM OrderEntity o WHERE o.certificate.id = :id", OrderEntity.class)
                .setParameter("id", id)
                .getResultList();

        return orders.isEmpty()
                ? Optional.empty()
                : Optional.of(orders.get(0));
    }

    @Override
    public Integer getCount() {
        return entityManager
                .createQuery("SELECT COUNT(o) FROM OrderEntity o", Long.class)
                .getSingleResult()
                .intValue();
    }

    @Override
    public Integer getCountByUserId(Integer id) {
        return entityManager
                .createQuery("SELECT COUNT(o) FROM OrderEntity o  WHERE o.user.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult()
                .intValue();
    }

}
