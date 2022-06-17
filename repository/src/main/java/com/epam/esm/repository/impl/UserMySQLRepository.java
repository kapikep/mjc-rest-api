package com.epam.esm.repository.impl;

import com.epam.esm.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserMySQLRepository {

    private EntityManager entityManager;

    public UserMySQLRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

//    @Transactional
    public List<UserEntity> readAllUsers(){
        List<UserEntity> users;

//        users = entityManager.createQuery("from user").getResultList();
        users = entityManager.createQuery("select u from UserEntity u").getResultList();

        return users;
    }
}
