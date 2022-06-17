package com.epam.esm.repository.impl;

import com.epam.esm.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@ActiveProfiles("test")
@SpringBootTest
//@DataJpaTest
class UserMySQLRepositoryTest {

    @Autowired
    private UserMySQLRepository repository;
    List<UserEntity> users;

    @Test
    @Transactional
    void readAllUserTest(){
//        List<UserEntity> users;
        users = repository.readAllUsers();
        System.out.println("---------------RESULT-------------------" + users);
        System.out.println("User orders");
        users.forEach(userEntity -> System.out.println(userEntity.getOrders()));
//        users.forEach(System.out::println);
    }
}