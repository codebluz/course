package com.courses.controllers;


import com.courses.dto.CourseMinDTO;
import com.courses.entities.CourseEntity;
import com.courses.enums.Check;
import com.courses.services.CourseServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/courses")
public class CourseController {

    @Autowired
    private CourseServices courseServices;

    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody CourseEntity courseEntity) {
        try {
           var response =  this.courseServices.create(courseEntity);
           return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Object> list() {
        try {
            var response = this.courseServices.list();
            if(response.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody CourseEntity body) {
        try {
            var response = this.courseServices.update(id, body);

            if(response == null) return ResponseEntity.noContent().build();

            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Object> changeStatus(@PathVariable UUID id) {
        try {
            var response = this.courseServices.setActiveStatus(id);

            if(response == null) return ResponseEntity.noContent().build();

            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        try {
            var response = this.courseServices.delete(id);

            if(!response) return ResponseEntity.noContent().build();

            return ResponseEntity.ok().build();
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
