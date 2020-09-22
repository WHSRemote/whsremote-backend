package com.whsremote.api.controller;

import com.whsremote.api.entity.Schedule;
import com.whsremote.api.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Controller
//@PreAuthorize("isAuthenticated()")
public class ScheduleController {
    private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    public ScheduleRepository repository;

    @GetMapping(path="/schedule", produces = "application/json")
    @ResponseBody
    public ResponseEntity getSchedule(@RequestParam(name = "id") String userId) {
        try {
            Schedule response = repository.findByUserId(userId);
            String body = "{\"1\":[],\"2\":[],\"4\":[],\"5\":[]}";
            if (response != null) {
                body = response.getJson();
            }
            return new ResponseEntity(body, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(path="/schedule", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity postSchedule(@RequestBody Schedule scheduleData) {
        if(scheduleData.getUserId().length() == 0 || scheduleData.getJson().length() == 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            Schedule existingSchedule = repository.findByUserId(scheduleData.getUserId());
            if (existingSchedule != null) {
                existingSchedule.setJson(scheduleData.getJson());
                repository.save(existingSchedule);
            } else {
                repository.save(scheduleData);
            }

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/schedule/update", produces = "application/json")
    @ResponseBody
    public ResponseEntity getScheduleUpdate() {
        String body = "{}";

        try {
            InputStream is = getFileFromResourceAsStream("schedule_update.txt");
            if (is == null) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            InputStreamReader streamReader =
                    new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("{}")) {
                    body = line;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(body, HttpStatus.OK);
    }

    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.
    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            System.err.println("File not found!");
            return null;
        } else {
            return inputStream;
        }

    }


}
