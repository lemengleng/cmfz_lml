package com.lml.controller;

import com.lml.entity.Course;
import com.lml.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @RequestMapping("queryAll")
    public Map queryAll(String uid){
        List<Course> courses = courseService.queryAll(new Course().setUser_id(uid));
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("course",courses);
        return map;
    }
    @RequestMapping("addCourse")
    public Map addCourse(Course course,String uid){
        course.setUser_id(uid);
        courseService.add(course);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("course",course);
        return map;
    }
    @RequestMapping("deleteCourse")
    public Map deleteCourse(String uid,String id){
        Course course = new Course();
        course.setId(id);
        course.setUser_id(uid);
        List<Course> courses = courseService.queryAll(course);
        courseService.delete(course);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("course",courses.get(0));
        return map;
    }
}
