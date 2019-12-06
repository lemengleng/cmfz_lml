package com.lml.service;

import com.lml.annotation.LogAnnotation;
import com.lml.dao.AlbumDao;
import com.lml.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryNum() {
        return albumDao.selectCount(new Album());
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Album> queryByPage(Integer begin, Integer size) {
        return albumDao.selectByRowBounds(new Album(),new RowBounds(begin,size));
    }
    @LogAnnotation(value = "添加专辑")
    @Override
    public void add(Album album) {
        albumDao.insert(album);
    }
    @LogAnnotation(value = "删除专辑")
    @Override
    public void delete(List<String> list) {
        albumDao.deleteByIdList(list);
    }
    @LogAnnotation(value = "更新专辑")
    @Override
    public void update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Album queryById(Album album) {
        return albumDao.selectByPrimaryKey(album);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Album> queryBySearch(Album album) {
        return albumDao.select(album);
    }
}
