package com.lml.dao;

import com.lml.entity.Admin;
import tk.mybatis.mapper.common.Mapper;

public interface AdminDao extends Mapper<Admin> {
    Admin queryAdminInfo(String username);
}
