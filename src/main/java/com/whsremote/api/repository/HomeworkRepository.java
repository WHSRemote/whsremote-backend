package com.whsremote.api.repository;

import com.whsremote.api.entity.Homework;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends CrudRepository<Homework, String> {
    Homework findByUserId(String userId);
}
