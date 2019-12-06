package com.lml.service;

import com.lml.entity.MapAddress;
import com.lml.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> queryByPage(Integer begin,Integer size);
    Integer queryNum();
    void add(User user);
    void update(User user);
    void deleteByList(String[] id);
    Integer queryByDate(String sex, Integer day);
    List<MapAddress> queryByLocation();
    User queryOne(User user);
    Map login(User user);
}
