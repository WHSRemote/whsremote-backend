package com.whsremote.api.controller;

import com.whsremote.api.entity.Homework;
import com.whsremote.api.entity.QuickLink;
import com.whsremote.api.repository.HomeworkRepository;
import com.whsremote.api.repository.QuickLinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@PreAuthorize("isAuthenticated()")
public class QuickLinkController {
    private static final Logger log = LoggerFactory.getLogger(QuickLinkController.class);

    @Autowired
    public QuickLinkRepository repository;

    @GetMapping(path="/quicklinks", produces = "application/json")
    @ResponseBody
    public ResponseEntity getQuickLinks(@RequestParam(name = "id") String userId) {
        try {
            QuickLink response = repository.findByUserId(userId);
            String body = "{}";
            if (response != null) {
                body = response.getJson();
            }
            return new ResponseEntity(body, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(path="/quicklinks", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity postQuickLinks(@RequestBody QuickLink linkData) {
        if(linkData.getUserId().length() == 0 || linkData.getJson().length() == 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            QuickLink existingHomework = repository.findByUserId(linkData.getUserId());
            if (existingHomework != null) {
                existingHomework.setJson(linkData.getJson());
                repository.save(existingHomework);
            } else {
                repository.save(linkData);
            }

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
