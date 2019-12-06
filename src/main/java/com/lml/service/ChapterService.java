package com.lml.service;

import com.lml.entity.Chapter;

import java.util.List;

public interface ChapterService {
    Integer queryNum(Chapter chapter);
    List<Chapter> queryByPage(Chapter chapter,Integer begin, Integer size);
    void add(Chapter chapter);
    void delete(List<String> list);
    void update(Chapter chapter);
    void deleteByAlbum_id(Chapter chapter);
    List<Chapter> queryBySearch(Chapter chapter);
}
