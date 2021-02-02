package com.whsremote.api.repository;

import com.whsremote.api.entity.Class;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<Class, String> {
    Class findByUserId(String userId);
}
