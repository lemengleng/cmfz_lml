package com.lml.service;

import com.lml.annotation.LogAnnotation;
import com.lml.dao.BannerDao;
import com.lml.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryNum() {
        return bannerDao.selectCount(new Banner());
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Banner> qyeryByPage(Integer begin, Integer end) {
        return bannerDao.selectByRowBounds(new Banner(),new RowBounds(begin,end));
    }
    @Override
    @LogAnnotation(value = "删除轮播图")
    public void deleteById(String id) {
        bannerDao.delete(new Banner(id,null,null,null,null,null,null));
    }
    @LogAnnotation(value = "更新轮播图")
    @Override
    public void updateById(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);

    }
    @LogAnnotation(value = "添加轮播图")
    @Override
    public Map add(Banner banner) {
        HashMap hashMap=new HashMap();
        bannerDao.insert(banner);
        hashMap.put("bannerId",banner.getId());
        hashMap.put("status",200);
        return hashMap;
    }
    @Override
    @LogAnnotation(value = "批量删除轮播图")
    public void deleteByList(String[] ids) {
        bannerDao.deleteByIdList(Arrays.asList(ids));
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Banner> queryBySearch(Banner banner) {
        return bannerDao.select(banner);
    }
}
