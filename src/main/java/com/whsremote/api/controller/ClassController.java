package com.whsremote.api.controller;

import com.whsremote.api.entity.Class;
import com.whsremote.api.repository.ClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClassController {
    private static final Logger log = LoggerFactory.getLogger(ClassController.class);

    @Autowired
    public ClassRepository repository;

    @GetMapping(path="/classes", produces = "application/json")
    @ResponseBody
    public ResponseEntity getClasses(@RequestParam(name = "id") String userId) {
        Class response = repository.findByUserId(userId);
        String body = "[]";
        if (response != null) {
            body = response.getJson();
        }
        return new ResponseEntity(body, HttpStatus.OK);
    }

    @PostMapping(path="/classes", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity postClasses(@RequestBody Class classData) {
        if(classData.getUserId().length() == 0 || classData.getJson().length() == 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Class existingClass = repository.findByUserId(classData.getUserId());
        if (existingClass != null) {
            existingClass.setJson(classData.getJson());
            repository.save(existingClass);
        } else {
            repository.save(classData);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
