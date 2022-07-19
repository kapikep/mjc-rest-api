package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.interf.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserMySQLRepository extends AbstractMySQLRepository<UserEntity> implements UserRepository {
    public UserMySQLRepository() {setClazz(UserEntity.class);}

}
