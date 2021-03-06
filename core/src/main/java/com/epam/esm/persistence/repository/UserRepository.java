package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.repository.entity.UserEntity;

/**
 * Extends {@link CrudRepository} interface for CRUD operations for {@link UserEntity} entity.
 */
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    /**
     * Checks whether user exists by user name
     * @param username user's username
     * @return true of false
     */
    boolean isExistByUsername(String username);

}
