package com.lml.service;

import com.lml.entity.Counter;
import com.lml.entity.Course;

import java.util.List;

public interface CounterService {
    List<Counter> queryAll(Counter counter);
    void add(Counter counter);
    void delete(Counter counter);
    void update(Counter counter);
}
