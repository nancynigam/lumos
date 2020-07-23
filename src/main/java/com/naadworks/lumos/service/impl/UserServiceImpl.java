package com.naadworks.lumos.service.impl;

import com.naadworks.lumos.entity.UserEntity;
import com.naadworks.lumos.entry.UserEntry;
import com.naadworks.lumos.exception.BaseException;
import com.naadworks.lumos.exception.DaoException;
import com.naadworks.lumos.repository.impl.UserDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAOImpl userDAO;

    public UserDAOImpl getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    public UserEntry findById(Long id) throws BaseException {
        UserEntity userEntity = null;
        if (!getUserDAO().exists(id))
            throw new BaseException("Id doesn't exists in the database");
        try {
            userEntity = getUserDAO().findOne(id);
            log.info("UserEntity with id = " + id + " found successfully");
        } catch (DaoException e1) {
            log.error("ERROR during find by id = ", e1);
        }
        return convertToEntry(userEntity);
    }

    //TODO : Is an exception necessary or a log.info would be enough
    public List<UserEntry> findAll() {
        List<UserEntry> userEntityList = new ArrayList<>();
        for (UserEntity ue : getUserDAO().findAll()) {
            userEntityList.add(convertToEntry(ue));
        }
//        if(userEntityList.size()<=0)
//            throw new BaseException("Table is empty");
        return userEntityList;

    }

    //TODO : is this the right way to check for duplicate entries
    public UserEntry create(UserEntry userEntry) throws BaseException {
        UserEntity userEntity = null;
        if (userEntry.getName() == null)
            throw new BaseException("Name field cannot be null.");
        List<UserEntity> userEntityList = new ArrayList<>();
        for (UserEntity ue : getUserDAO().findAll()) {
            if (ue.getName().equals(userEntry.getName()) && ue.getAge().equals(userEntry.getAge()) && ue.getCountry().equals(userEntry.getCountry()))
                throw new BaseException("Cannot create new user as a duplicate element exists.");
        }
        try {
            userEntity = getUserDAO().save(convertToEntity(userEntry));
            log.info("New UserEntity with id = " + userEntity.getId() + " created successfully");
        } catch (DaoException e1) {
            log.error("ERROR while creating a new user as the given entry is null. " + e1);
        }
        return convertToEntry(userEntity);
    }

    public UserEntry update(UserEntry userEntry, Long id) throws BaseException {
        userEntry.setId(id);
        UserEntity userEntity = null;
        if (!getUserDAO().exists(id))
            throw new BaseException("Id doesn't exists in the database");
        try {
            userEntity = getUserDAO().save(convertToEntity(userEntry));
            log.info("UserEntity with id = " + userEntity.getId() + " updated successfully");
        } catch (DaoException e1) {
            log.error("ERROR while updating user entity as the given entry is null. " + e1);
        }
        return convertToEntry(userEntity);
    }

    public void delete(Long id) throws BaseException {
        if (!getUserDAO().exists(id))
            throw new BaseException("Id doesn't exists in the database");
        try {
            getUserDAO().delete(id);
            log.info("UserEntity with id = " + id + " deleted successfully");
        } catch (DaoException e1) {
            log.error("ERROR while deleting user entity as the given id is null. " + e1);
        }
    }

    public UserEntry convertToEntry(UserEntity userEntity) {
        UserEntry userEntry = null;
        if (userEntity != null) {
            userEntry = new UserEntry();
            try {
                BeanUtils.copyProperties(userEntity, userEntry);
            } catch (BeansException ex) {
                log.error("Error while converting to entry = ", ex);
                return null;
            }
        }
        return userEntry;
    }

    public UserEntity convertToEntity(UserEntry userEntry) {
        UserEntity userEntity = null;
        if (userEntry != null) {
            userEntity = new UserEntity();
            try {
                BeanUtils.copyProperties(userEntry, userEntity);
            } catch (Exception ex) {
                log.error("error while converting to entity");
                return null;
            }
        }
        return userEntity;
    }
}
