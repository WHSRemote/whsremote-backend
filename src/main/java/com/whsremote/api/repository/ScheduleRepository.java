package com.whsremote.api.repository;

import com.whsremote.api.entity.Class;
import com.whsremote.api.entity.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, String> {
    Schedule findByUserId(String userId);
}
