package com.courses.Repositories;

import com.courses.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRespository extends JpaRepository<CourseEntity, UUID> {
}
