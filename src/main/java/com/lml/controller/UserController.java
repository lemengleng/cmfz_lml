package com.lml.controller;

import com.lml.entity.Banner;
import com.lml.entity.MapAddress;
import com.lml.entity.User;
import com.lml.service.UserService;
import com.lml.util.HttpUtil;
import com.lml.util.MD5Utils;
import com.lml.util.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;
    @RequestMapping("queryByPage")
    public Map<String,Object> queryByPage(Integer page, Integer rows){
        Map<String,Object> map=new HashMap<String, Object>();
        Integer records=null;
        List<User> list=null;
        /*if(_search){
            list=empService.queryBySearch(page*rows-rows,rows,searchString,searchField,searchOper);
            System.out.println(list);
            records=empService.queryBySearchNum(searchString,searchField,searchOper);
        }else {
            records=empService.queryNum();
            list=empService.queryAll(page*rows-rows,rows);
        }*/
        records=userService.queryNum();
        list=userService.queryByPage(page*rows-rows,rows);
        Integer total=null;
        if(records%rows==0){
            total=records/rows;
        }else {
            total=records/rows+1;
        }
        map.put("rows",list);
        map.put("page",page);
        map.put("total",total);
        map.put("records",records);
        return map;
    }
    @RequestMapping("cud")
    public Map cud(String oper,User user,String[] id){
        Map hashMap=new HashMap();
        if (oper.equals("add")){
            user.setId(null);
            userService.add(user);
            hashMap.put("userId",user.getId());
            hashMap.put("status",200);
        }
        if(oper.equals("edit")){
            user.setPhoto(null);
            hashMap.put("userId",user.getId());
            hashMap.put("status",200);
            userService.update(user);
        }
        if ("del".equals(oper)){
            userService.deleteByList(id);
        }
        return hashMap;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile photo, String userId, HttpServletRequest request) throws Exception {
        if (photo.getSize()!=0){
            String uri= HttpUtil.getHttpUrl(photo,request,"/upload/userPhoto/");
            User user=new User();
            user.setPhoto(uri);
            user.setId(userId);
            userService.update(user);
        }

    }
    @RequestMapping("queryByDate")
    public Map<String,List<Integer>> queryByDate(){
        Integer day1=userService.queryByDate("男",1);
        Integer day7=userService.queryByDate("男",7);
        Integer day30=userService.queryByDate("男",30);
        Integer day365=userService.queryByDate("男",365);
        Map<String,List<Integer>> map=new HashMap();
        map.put("man", Arrays.asList(day1,day7,day30,day365));
        Integer aday1=userService.queryByDate("女",1);
        Integer aday7=userService.queryByDate("女",7);
        Integer aday30=userService.queryByDate("女",30);
        Integer aday365=userService.queryByDate("女",365);
        map.put("woman", Arrays.asList(aday1,aday7,aday30,aday365));
        return map;
    }
    @RequestMapping("queryByLocation")
    public List<MapAddress> queryByLocation(){
        List<MapAddress> list=userService.queryByLocation();
        return list;
    }
    @RequestMapping("login")
    public Map login(User user){
        Map<String,Object> map=null;
        try {
            map=userService.login(user);
        } catch (Exception e) {
            map.put("status",-200);
            map.put("message","预期之外的错误");
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("sendSms")
    public Map sendSms(String phone){
        Map<String,Object> map=new HashMap<>();
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        String code=MD5Utils.getNum();
        try {
            SendSms.sendSms(phone,code);
            stringStringValueOperations.set("code",code,5, TimeUnit.MINUTES);
            map.put("status",200);
        } catch (Exception e) {
            map.put("status",-200);
            map.put("message","验证码发送失败");
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("regist")
    public Map regist(User user,String code){
        Map<String,Object> map=new HashMap<>();
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        if(stringStringValueOperations.get("code").equals(code)){
            String salt = MD5Utils.getSalt();
            user.setPassword(MD5Utils.getPassword(user.getPassword()+salt));
            user.setSalt(salt);
            user.setRigest_date(new Date());
            userService.add(user);
            map.put("status",200);
        }else {
            map.put("status",-200);
            map.put("message","验证码错误");
        }
        return map;
    }
    @RequestMapping("addInformation")
    public Map addInformation(User user,MultipartFile photo,HttpServletRequest request){
        if (photo.getSize()!=0){
            String url=null;
            try {
                url=HttpUtil.getHttpUrl(photo,request,"/upload/userPhoto/");
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setPhoto(url);
        }
        userService.update(user);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("user",new User().setId(user.getId()));
        return map;
    }
    @RequestMapping("updateUser")
    public Map updateUser(User user,MultipartFile photo,HttpServletRequest request){
        if (photo.getSize()!=0){
            String url=null;
            try {
                url=HttpUtil.getHttpUrl(photo,request,"/upload/userPhoto/");
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setPhoto(url);
        }
        userService.update(user);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("user",new User().setId(user.getId()));
        return map;
    }
}
