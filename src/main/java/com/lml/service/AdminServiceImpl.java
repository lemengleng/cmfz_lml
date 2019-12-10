package com.lml.service;

import com.lml.dao.AdminDao;
import com.lml.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Admin queryOne(String username) {
        return adminDao.selectOne(new Admin(null, username, null, null));
    }
}
