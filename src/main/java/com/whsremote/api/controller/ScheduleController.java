package com.whsremote.api.controller;

import com.whsremote.api.entity.Schedule;
import com.whsremote.api.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Controller
public class ScheduleController {
    private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    public ScheduleRepository repository;

    @GetMapping(path="/schedule", produces = "application/json")
    @ResponseBody
    public ResponseEntity getSchedule(@RequestParam(name = "id") String userId) {
        try {
            Schedule response = repository.findByUserId(userId);
            String body = "{}";
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
            File myObj = new File("schedule_update.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (!data.equals("{}")) {
                    body = data;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return new ResponseEntity(body, HttpStatus.OK);
    }

}
