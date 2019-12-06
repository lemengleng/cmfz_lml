package com.lml.service;

import com.lml.entity.Article;
import com.lml.entity.Banner;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    Integer queryNum();
    List<Article> qyeryByPage(Integer begin, Integer end);
    void deleteById(Article article);
    void updateById(Article article);
    void add(Article article);
    void deleteByList(String[] ids);
    List<Article> queryBySearch(Article article);
}
