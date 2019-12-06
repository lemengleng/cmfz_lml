package com.lml.service;

import com.lml.dao.CourseDao;
import com.lml.entity.Course;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Course> queryAll(Course course) {
        return courseDao.select(course);
    }

    @Override
    public void add(Course course) {
        courseDao.insert(course);
    }

    @Override
    public void delete(Course course) {
        courseDao.delete(course);
    }

    @Override
    public void update(Course course) {
        courseDao.updateByPrimaryKeySelective(course);
    }
}
