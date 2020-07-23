package com.naadworks.lumos.repository.impl;

import com.naadworks.lumos.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAOImpl extends CrudRepository<UserEntity, Long> {
}
