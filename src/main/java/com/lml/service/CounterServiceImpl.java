package com.lml.service;

import com.lml.dao.CounterDao;
import com.lml.entity.Counter;
import com.lml.entity.Course;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    private CounterDao counterDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Counter> queryAll(Counter counter) {
        return counterDao.select(counter);
    }

    @Override
    public void add(Counter counter) {
        counterDao.insert(counter);
    }

    @Override
    public void delete(Counter counter) {
        counterDao.delete(counter);
    }

    @Override
    public void update(Counter counter) {
        counterDao.updateByPrimaryKeySelective(counter);
    }


}
