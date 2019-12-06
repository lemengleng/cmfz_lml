package com.lml.service;

import com.lml.annotation.LogAnnotation;
import com.lml.dao.GuruDao;
import com.lml.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    private GuruDao guruDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Guru> queryAll(Guru guru) {
        return guruDao.select(guru);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryNum() {
        return guruDao.selectCount(new Guru());
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Guru> qyeryByPage(Integer begin, Integer end) {
        return guruDao.selectByRowBounds(new Guru(),new RowBounds(begin,end));
    }
    @LogAnnotation(value = "更新上师")
    @Override
    public void updateById(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }
    @LogAnnotation(value = "添加上师")
    @Override
    public void add(Guru guru) {
        guruDao.insert(guru);
    }
    @LogAnnotation(value = "批量删除上师")
    @Override
    public void deleteByList(String[] ids) {
        guruDao.deleteByIdList(Arrays.asList(ids));
    }
}
