package com.epam.esm.repository.impl;

import com.epam.esm.entity.UserEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.interf.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserMySQLRepository extends AbstractMySQLRepository<UserEntity> implements UserRepository {
    public static final String USER_WITH_HIGHEST_COST = "select o.user from OrderForGiftCertificateEntity o group by o.user having sum (o.totalAmount) >=" +
            " ALL(select sum (int.totalAmount) from OrderForGiftCertificateEntity int group by int.user)";

    public UserMySQLRepository() {
        setClazz(UserEntity.class);
    }

    @Override
    public List<UserEntity> findUserWithHighestCostOfAllOrders() {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                USER_WITH_HIGHEST_COST, UserEntity.class);

        return query.getResultList();
    }
}
