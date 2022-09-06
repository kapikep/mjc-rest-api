package com.epam.esm.repository.impl;

import com.epam.esm.entity.UserEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.interf.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * MySQL repository for UserEntity
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public class UserMySQLRepository extends AbstractMySQLRepository<UserEntity> implements UserRepository {
    public static final String USER_WITH_HIGHEST_COST = "select o.user from OrderForGiftCertificateEntity o group by o.user having sum (o.totalAmount) >=" +
            " ALL(select sum (int.totalAmount) from OrderForGiftCertificateEntity int group by int.user)";

    public UserMySQLRepository() {
        setClazz(UserEntity.class);
    }

    /**
     * Find user with the highest cost of all orders.
     * If there are several users match to the condition, all matching users are returned.
     *
     * @return List with UserEntities.
     */
    @Override
    public List<UserEntity> findUserWithHighestCostOfAllOrders() {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                USER_WITH_HIGHEST_COST, UserEntity.class);

        return query.getResultList();
    }
}
