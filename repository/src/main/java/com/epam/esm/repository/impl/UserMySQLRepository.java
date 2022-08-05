package com.epam.esm.repository.impl;

import com.epam.esm.entity.UserEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.interf.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserMySQLRepository extends AbstractMySQLRepository<UserEntity> implements UserRepository {
    public UserMySQLRepository() {
        setClazz(UserEntity.class);
    }

    @Override
    public List<UserEntity> findUserWithHighestCostOfAllOrders() {
//        TypedQuery<UserEntity> query = entityManager.createQuery(
//                "select o.user from OrderForGiftCertificateEntity o group by o.user having sum (o.totalAmount) = " +
//                        "(select sum (o.totalAmount) from OrderForGiftCertificateEntity group by o.user order by sum (o.totalAmount) DESC)",
//                UserEntity.class);

        TypedQuery<UserEntity> query = entityManager.createQuery(
                "select o.user from OrderForGiftCertificateEntity o group by o.user having sum (o.totalAmount) >=" +
                        " ALL(select sum (int.totalAmount) from OrderForGiftCertificateEntity int group by int.user)",
                UserEntity.class);

        return query.getResultList();
    }
}
