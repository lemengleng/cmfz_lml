package com.lml.dao;

import com.lml.entity.MapAddress;
import com.lml.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {
    Integer queryByDate(@Param("sex") String sex,@Param("day") Integer day);
    List<MapAddress> queryByLocation();
}
