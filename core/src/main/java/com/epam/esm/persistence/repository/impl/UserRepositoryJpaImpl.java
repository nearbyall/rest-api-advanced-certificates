package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.persistence.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryJpaImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Integer> create(UserEntity obj) {
        entityManager.persist(obj);

        Integer id = obj.getId();
        return id != 0
                ? Optional.of(id)
                : Optional.empty();
    }

    @Override
    public List<UserEntity> getAll(int limit, int offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<UserEntity> getById(Integer id) {
        List<UserEntity> users = entityManager
                .createQuery("SELECT u FROM UserEntity u WHERE u.id = :id", UserEntity.class)
                .setParameter("id", id)
                .getResultList();

        return users.isEmpty()
                ? Optional.empty()
                : Optional.of(users.get(0));
    }

    @Override
    public Optional<UserEntity> getByUsername(String username) {
        List<UserEntity> users = entityManager
                .createQuery("SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getResultList();

        return users.isEmpty()
                ? Optional.empty()
                : Optional.of(users.get(0));
    }

    @Override
    public Integer update(UserEntity user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer delete(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isExistByUsername(String username) {
        List<UserEntity> users = entityManager
                .createQuery("SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getResultList();

        return !users.isEmpty();
    }

}
