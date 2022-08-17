package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.OrderForGiftCertificateRepository;
import com.epam.esm.repository.interf.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.repository.impl.EntityFactory.EntityFactory.getNewCriteriaWithDefaultVal;
import static com.epam.esm.repository.impl.EntityFactory.UserEntityFactory.getNewUserEntityId1;
import static com.epam.esm.repository.impl.EntityFactory.UserEntityFactory.getNewUserEntityId3;
import static com.epam.esm.repository.impl.EntityFactory.UserEntityFactory.getNewUserEntityId4;
import static com.epam.esm.repository.impl.EntityFactory.UserEntityFactory.getNewUserEntityId5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@EnableJpaAuditing
class UserMySQLRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderForGiftCertificateRepository orderRepository;
    List<UserEntity> users;

    @Test
    void readAllUserPaginatedTest() throws RepositoryException {
        List<UserEntity> users;
        CriteriaEntity cr = getNewCriteriaWithDefaultVal();
        users = userRepository.readPaginated(cr);
        assertEquals(5, users.size());
        users.forEach(Assertions::assertNotNull);
        assertEquals(users.get(0), getNewUserEntityId1());
        assertEquals(users.get(4), getNewUserEntityId5());

        cr.setSorting("-id");
        users = userRepository.readPaginated(cr);
        assertEquals(5, users.size());
        users.forEach(Assertions::assertNotNull);
        assertEquals(users.get(0), getNewUserEntityId5());
        assertEquals(users.get(4), getNewUserEntityId1());
    }

    @Test
    void readAllUsersPaginatedWithExceptionTest() {
        CriteriaEntity cr = new CriteriaEntity();

        cr.setPage(2);
        cr.setSize(20);

        RepositoryException e = assertThrows(RepositoryException.class, () -> userRepository.readPaginated(cr));
        assertEquals("Page must be 1", e.getMessage());
    }

    @Test
    void readUserByIdTest() throws RepositoryException {
        UserEntity expectedTag = getNewUserEntityId5();
        UserEntity actualTag = userRepository.readById(5);

        assertEquals(expectedTag, actualTag);
        RepositoryException e = assertThrows(RepositoryException.class, () -> userRepository.readById(6));
        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
    }

    @Test
    void findUserWithHighestCostOfAllOrdersTest() {
        users = userRepository.findUserWithHighestCostOfAllOrders();
        assertEquals(1, users.size());
        assertEquals(getNewUserEntityId3(), users.get(0));
    }

    @Test
    void findUserWithHighestCostAddNewOrderTest() throws RepositoryException {
        orderRepository.create(new OrderForGiftCertificateEntity(0, LocalDateTime.now(), new BigDecimal(130),
                getNewUserEntityId4(), null));
        users = userRepository.findUserWithHighestCostOfAllOrders();
        assertEquals(2, users.size());
        assertEquals(getNewUserEntityId3(), users.get(0));
        assertEquals(getNewUserEntityId4(), users.get(1));
    }

//    @Test
//    void put1000users(){
//        Random r = new Random();
//        UserEntity user;
//        long phoneNumber = 375_292_899_999L;
//        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789&";
//
//        for (int i = 0; i < 1000; i++) {
//            StringBuilder password = new StringBuilder();
//            for (int j = 0; j < Math.random() * (15 - 6) + 6; j++) {
//                char ch = alphabet.charAt(r.nextInt(alphabet.length()));
//                password.append(ch);
//            }
//            try {
//                phoneNumber += i;
//                user = new UserEntity(0L, "First name" + r.nextInt(100),
//                        "Second name" + r.nextInt(100), "login" + i, password.toString(),
//                        "+" + phoneNumber);
//                repository.create(user);
//            } catch (Exception ignored) {
//            }
//        }
//    }

}