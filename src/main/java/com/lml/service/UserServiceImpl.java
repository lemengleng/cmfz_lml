package com.lml.service;

import com.lml.annotation.LogAnnotation;
import com.lml.dao.UserDao;
import com.lml.entity.MapAddress;
import com.lml.entity.User;
import com.lml.util.MD5Utils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> queryByPage(Integer begin,Integer size) {
        return userDao.selectByRowBounds(new User(),new RowBounds(begin,size));
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryNum() {
        return userDao.selectCount(new User());
    }
    @LogAnnotation(value = "添加用户")
    @Override
    public void add(User user) {
        userDao.insert(user);
    }
    @LogAnnotation(value = "修改用户")
    @Override
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }
    @LogAnnotation(value = "批量删除用户")
    @Override
    public void deleteByList(String[] id) {
        userDao.deleteByIdList(Arrays.asList(id));
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryByDate(String sex, Integer day) {
        return userDao.queryByDate(sex,day);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<MapAddress> queryByLocation() {
        return userDao.queryByLocation();
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User queryOne(User user) {
        return userDao.selectOne(user);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map login(User user) {
        Map<String,Object> map=new HashMap<>();
        if(user.getPhone()!=null){
            User user1=userDao.selectOne(new User().setPhone(user.getPhone()));
            if (user1!=null){
                String password= MD5Utils.getPassword(user.getPassword()+user1.getSalt());
                if (user1.getPassword().equals(password)){
                    map.put("status",200);
                    map.put("user",user1);
                }else {
                    map.put("status",-200);
                    map.put("message","用户密码错误");
                }
            }else {
                map.put("status",-200);
                map.put("message","用户不存在");
            }
        }else {
            map.put("status",-200);
            map.put("message","用户为空");
        }
        return map;
    }
}
