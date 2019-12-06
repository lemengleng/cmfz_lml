package com.lml.controller;

import com.lml.entity.Banner;
import com.lml.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @RequestMapping("queryByPage")
    public Map<String,Object> queryByPage(Integer page, Integer rows, String searchString, Boolean _search, String searchField, String searchOper){
        Map<String,Object> map=new HashMap<String, Object>();
        Integer records=null;
        List<Banner> list=null;
        /*if(_search){
            list=empService.queryBySearch(page*rows-rows,rows,searchString,searchField,searchOper);
            System.out.println(list);
            records=empService.queryBySearchNum(searchString,searchField,searchOper);
        }else {
            records=empService.queryNum();
            list=empService.queryAll(page*rows-rows,rows);
        }*/
        records=bannerService.queryNum();
        list=bannerService.qyeryByPage(page*rows-rows,rows);
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
    public Map cud(String oper,Banner banner,String[] id){
        Map hashMap=new HashMap();
        if (oper.equals("add")){
            banner.setId(null);
            hashMap=bannerService.add(banner);
        }
        if(oper.equals("edit")){
            banner.setUrl(null);
            hashMap.put("bannerId",banner.getId());
            hashMap.put("status",200);
            bannerService.updateById(banner);
        }
        if ("del".equals(oper)){
            bannerService.deleteByList(id);
        }
        return hashMap;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile url, String bannerId, HttpServletRequest request) throws Exception {
        String realPath=request.getSession().getServletContext().getRealPath("/img");
        if (url.getSize()!=0){
            File file=new File(realPath);
            if (!file.exists()){
                file.mkdirs();
            }
            String originalFilename=url.getOriginalFilename();
            originalFilename=new Date().getTime()+"_"+originalFilename;
            url.transferTo(new File(realPath,originalFilename));
            String http=request.getScheme();
            String loaclhost= InetAddress.getLocalHost().toString();
            int serverPort=request.getServerPort();
            String contextPath=request.getContextPath();
            String uri=http+"://"+loaclhost.split("/")[1]+":"+serverPort+contextPath+"/img/"+originalFilename;
            Banner banner=new Banner();
            banner.setId(bannerId);
            banner.setUrl(uri);
            bannerService.updateById(banner);
        }

    }
}
