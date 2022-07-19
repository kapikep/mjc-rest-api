package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.repository.exception.RepositoryException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@ActiveProfiles("test")
@SpringBootTest
class UserMySQLRepositoryTest {

    @Autowired
    private UserMySQLRepository repository;
    List<UserEntity> users;

    @Test
    @Transactional
    void readAllUserTest() throws RepositoryException {
        CriteriaEntity cr = new CriteriaEntity();
        cr.setPage(1);
        cr.setSize(2);
        List<UserEntity> users;
        users = repository.readPage(cr);
        System.out.println("---------------RESULT-------------------");
        users.forEach(System.out::println);
    }
}