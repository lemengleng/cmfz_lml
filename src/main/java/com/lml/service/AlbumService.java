package com.lml.service;

import com.lml.entity.Album;

import java.util.List;


public interface AlbumService {
    Integer queryNum();
    List<Album> queryByPage(Integer begin,Integer size);
    void add(Album album);
    void delete(List<String> list);
    void update(Album album);
    Album queryById(Album album);
    List<Album> queryBySearch(Album album);
}
