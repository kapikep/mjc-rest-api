package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.repository.exception.RepositoryException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
class UserMySQLRepositoryTest {

    @Autowired
    private UserMySQLRepository repository;
    List<UserEntity> users;

    @Test
    void findUserWithHighestCostOfAllOrdersTest() {
        users = repository.findUserWithHighestCostOfAllOrders();
        System.out.println(users);
        assertEquals(2, users.size());
    }

    @Test
    void put1000users(){
        Random r = new Random();
        UserEntity user;
        long phoneNumber = 375_292_899_999L;
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789&";

        for (int i = 0; i < 1000; i++) {
            StringBuilder password = new StringBuilder();
            for (int j = 0; j < Math.random() * (15 - 6) + 6; j++) {
                char ch = alphabet.charAt(r.nextInt(alphabet.length()));
                password.append(ch);
            }
            try {
                phoneNumber += i;
                user = new UserEntity(0L, "First name" + r.nextInt(100),
                        "Second name" + r.nextInt(100), "login" + i, password.toString(),
                        "+" + phoneNumber);
                repository.create(user);
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    @Transactional
    void readAllUserTest() throws RepositoryException {
        CriteriaEntity cr = new CriteriaEntity();
        cr.setPage(1);
        cr.setSize(2);
        List<UserEntity> users;
        users = repository.readAllPaginated(cr);
        System.out.println("---------------RESULT-------------------");
        users.forEach(System.out::println);
    }
}