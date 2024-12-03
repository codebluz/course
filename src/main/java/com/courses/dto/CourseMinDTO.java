package com.courses.dto;

import com.courses.entities.CourseEntity;
import com.courses.enums.Check;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CourseMinDTO {
    private UUID id;
    private String name;
    private String category;
    private Check active;

    public CourseMinDTO(CourseEntity courseEntity) {
        this.id = courseEntity.getId();
        this.name = courseEntity.getName();
        this.category = courseEntity.getCategory();
        this.active = courseEntity.getActive();
    }
}
