package com.courses.services;

import com.courses.Repositories.CourseRespository;
import com.courses.dto.CourseDTO;
import com.courses.dto.CourseMinDTO;
import com.courses.entities.CourseEntity;
import com.courses.enums.Check;
import com.courses.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseServices {

    @Autowired
    private CourseRespository courseRespository;

    public CourseDTO create(CourseEntity courseEntity) {
        var course = this.courseRespository.save(courseEntity);

        return CourseDTO
                .builder()
                .id(course.getId())
                .name(course.getName())
                .category(course.getCategory())
                .active(course.getActive())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }

    public List<CourseMinDTO> list() {
        var result = this.courseRespository.findAll();
        return result.stream().map(CourseMinDTO::new).toList();
    }

    public CourseDTO update(UUID id, CourseEntity body) {
        var course = this.courseRespository.findById(id).orElse(null);

        if (course == null) return null;

        utils.copyNonNullProperties(body, course);

        var courseUpdated = this.courseRespository.save(course);

        return CourseDTO
                .builder()
                .id(courseUpdated.getId())
                .name(courseUpdated.getName())
                .category(courseUpdated.getCategory())
                .active(courseUpdated.getActive())
                .createdAt(courseUpdated.getCreatedAt())
                .updatedAt(courseUpdated.getUpdatedAt())
                .build();
    }

    public CourseDTO setActiveStatus(UUID id) {
        var course = this.courseRespository.findById(id).orElse(null);

        if (course == null) return null;

        var newActiveStatus = course.getActive() == Check.active ? Check.inactive : Check.active;

        course.setActive(newActiveStatus);

        var courseUpdated = this.courseRespository.save(course);

        return CourseDTO
                .builder()
                .id(courseUpdated.getId())
                .name(courseUpdated.getName())
                .category(courseUpdated.getCategory())
                .active(courseUpdated.getActive())
                .createdAt(courseUpdated.getCreatedAt())
                .updatedAt(courseUpdated.getUpdatedAt())
                .build();
    }

    public Boolean delete(UUID id) {
        var course = this.courseRespository.findById(id).orElse(null);

        if (course == null) return false;

        this.courseRespository.deleteById(id);

        return true;
    }
}
