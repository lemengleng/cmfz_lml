package com.lml.service;

import com.lml.annotation.LogAnnotation;
import com.lml.dao.ChapterDao;
import com.lml.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryNum(Chapter chapter) {
        return chapterDao.selectCount(chapter);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Chapter> queryByPage(Chapter chapter,Integer begin, Integer size) {
        return chapterDao.selectByRowBounds(chapter,new RowBounds(begin,size));
    }
    @LogAnnotation(value = "添加章节")
    @Override
    public void add(Chapter chapter) {
        chapterDao.insert(chapter);
    }
    @LogAnnotation(value = "删除多个章节")
    @Override
    public void delete(List<String> list) {
        chapterDao.deleteByIdList(list);
    }
    @LogAnnotation(value = "更新章节")
    @Override
    public void update(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }
    @LogAnnotation(value = "删除单个章节")
    @Override
    public void deleteByAlbum_id(Chapter chapter) {
        chapterDao.delete(chapter);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Chapter> queryBySearch(Chapter chapter) {
        return chapterDao.select(chapter);
    }
}
