package com.lml.service;

import com.lml.entity.Banner;
import com.lml.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    List<Guru> queryAll(Guru guru);
    Integer queryNum();
    List<Guru> qyeryByPage(Integer begin, Integer end);
    void updateById(Guru guru);
    void add(Guru guru);
    void deleteByList(String[] ids);
}
