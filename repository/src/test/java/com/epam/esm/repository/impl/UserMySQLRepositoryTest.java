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

import static com.epam.esm.repository.constant.ExceptionMes.INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0;
import static com.epam.esm.repository.constant.ExceptionMes.PAGE_MUST_BE_1;
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
        assertEquals(PAGE_MUST_BE_1, e.getMessage());
    }

    @Test
    void readUserByIdTest() throws RepositoryException {
        UserEntity expectedTag = getNewUserEntityId5();
        UserEntity actualTag = userRepository.readById(5);

        assertEquals(expectedTag, actualTag);
        RepositoryException e = assertThrows(RepositoryException.class, () -> userRepository.readById(6));
        assertEquals(INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0, e.getMessage());
    }

    @Test
    void findUserWithHighestCostOfAllOrdersTest() {
        users = userRepository.findUserWithHighestCostOfAllOrders();
        assertEquals(1, users.size());
        assertEquals(getNewUserEntityId3(), users.get(0));
    }

    @Test
    void findUserWithHighestCostAddNewOrderTest() {
        orderRepository.create(new OrderForGiftCertificateEntity(0, LocalDateTime.now(), new BigDecimal(130),
                getNewUserEntityId4(), null));
        users = userRepository.findUserWithHighestCostOfAllOrders();
        assertEquals(2, users.size());
        assertEquals(getNewUserEntityId3(), users.get(0));
        assertEquals(getNewUserEntityId4(), users.get(1));
    }
}