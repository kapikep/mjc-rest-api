package com.epam.esm.repository.interf;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;

public interface UserRepository {
    List<UserEntity> readPaginated(CriteriaEntity cr) throws RepositoryException;

    UserEntity readById(long id) throws RepositoryException;

    List<UserEntity> findUserWithHighestCostOfAllOrders();
}
