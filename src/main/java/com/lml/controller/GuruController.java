package com.lml.controller;

import com.lml.entity.Guru;
import com.lml.entity.User;
import com.lml.service.GuruService;
import com.lml.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    private GuruService guruService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @RequestMapping("queryAll")
    public Map queryAll(String uid){
        List<Guru> gurus = guruService.queryAll(new Guru());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("list",gurus);
        return map;
    }
    @RequestMapping("queryByPage")
    public Map<String,Object> queryByPage(Integer page, Integer rows){
        Map<String,Object> map=new HashMap<String, Object>();
        Integer records=null;
        List<Guru> list=null;
        /*if(_search){
            list=empService.queryBySearch(page*rows-rows,rows,searchString,searchField,searchOper);
            System.out.println(list);
            records=empService.queryBySearchNum(searchString,searchField,searchOper);
        }else {
            records=empService.queryNum();
            list=empService.queryAll(page*rows-rows,rows);
        }*/
        records=guruService.queryNum();
        list=guruService.qyeryByPage(page*rows-rows,rows);
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
    public Map cud(String oper,Guru guru,String[] id){
        Map hashMap=new HashMap();
        if (oper.equals("add")){
            guru.setId(null);
            guruService.add(guru);
            hashMap.put("guruId",guru.getId());
            hashMap.put("status",200);
        }
        if(oper.equals("edit")){
            guru.setPhoto(null);
            hashMap.put("guruId",guru.getId());
            hashMap.put("status",200);
            guruService.updateById(guru);
        }
        if ("del".equals(oper)){
            guruService.deleteByList(id);
        }
        return hashMap;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile photo, String guruId, HttpServletRequest request) throws Exception {
        if (photo.getSize()!=0){
            String uri= HttpUtil.getHttpUrl(photo,request,"/upload/userPhoto/");
            Guru guru=new Guru();
            guru.setPhoto(uri);
            guru.setId(guruId);
            guruService.updateById(guru);
        }

    }
    @RequestMapping("addGuru")
    public Map addGuru(String uid,String id){
        SetOperations<String, String> sst = stringRedisTemplate.opsForSet();
        sst.add(uid,id);
        sst.add(id,uid);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("message","添加成功");
        Set<String> members = sst.members(uid);
        ArrayList<Object> l = new ArrayList<>();
        for (String str:members){
            List<Guru> gurus = guruService.queryAll(new Guru().setId(str));
            l.add(gurus.get(0));
        }
        map.put("list",l);
        return map;
    }

}
