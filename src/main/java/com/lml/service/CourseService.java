package com.lml.service;

import com.lml.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> queryAll(Course course);
    void add(Course course);
    void delete(Course course);
    void update(Course course);
}
