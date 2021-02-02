package com.whsremote.api.controller;

import com.whsremote.api.entity.Homework;
import com.whsremote.api.entity.Schedule;
import com.whsremote.api.repository.HomeworkRepository;
import com.whsremote.api.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Controller
//@PreAuthorize("isAuthenticated()")
public class HomeworkController {
    private static final Logger log = LoggerFactory.getLogger(HomeworkController.class);

    @Autowired
    public HomeworkRepository repository;

    @GetMapping(path="/homework", produces = "application/json")
    @ResponseBody
    public ResponseEntity getHomework(@RequestParam(name = "id") String userId) {
        try {
            Homework response = repository.findByUserId(userId);
            String body = "{}";
            if (response != null) {
                body = response.getJson();
            }
            return new ResponseEntity(body, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(path="/homework", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity postHomework(@RequestBody Homework homeworkData) {
        if(homeworkData.getUserId().length() == 0 || homeworkData.getJson().length() == 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            Homework existingHomework = repository.findByUserId(homeworkData.getUserId());
            if (existingHomework != null) {
                existingHomework.setJson(homeworkData.getJson());
                repository.save(existingHomework);
            } else {
                repository.save(homeworkData);
            }

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
