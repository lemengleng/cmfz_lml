package com.lml.service;

import com.lml.annotation.LogAnnotation;
import com.lml.dao.ArticleDao;
import com.lml.entity.Article;
import lombok.extern.java.Log;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryNum() {
        return articleDao.selectCount(new Article());
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Article> qyeryByPage(Integer begin, Integer end) {
        return articleDao.selectByRowBounds(new Article(),new RowBounds(begin,end));
    }
    @LogAnnotation(value = "删除文章")
    @Override
    public void deleteById(Article article) {
        articleDao.delete(article);
    }
    @LogAnnotation(value = "更新文章")
    @Override
    public void updateById(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }
    @LogAnnotation(value = "添加文章")
    @Override
    public void add(Article article) {
        articleDao.insert(article);
    }
    @LogAnnotation(value = "批量删除文章")
    @Override
    public void deleteByList(String[] ids) {
        articleDao.deleteByIdList(Arrays.asList(ids));
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Article> queryBySearch(Article article) {
        return articleDao.selectByRowBounds(article,new RowBounds(0,6));
    }
}
