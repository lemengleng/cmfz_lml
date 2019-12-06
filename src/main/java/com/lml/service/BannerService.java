package com.lml.service;

import com.lml.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    Integer queryNum();
    List<Banner> qyeryByPage(Integer begin,Integer end);
    void deleteById(String id);
    void updateById(Banner banner);
    Map add(Banner banner);
    void deleteByList(String[] ids);
    List<Banner> queryBySearch(Banner banner);
}
