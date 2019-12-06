package com.lml.controller;

import com.lml.entity.Album;
import com.lml.entity.Chapter;
import com.lml.service.AlbumService;
import com.lml.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.util.*;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("queryByPage")
    public Map<String,Object> queryByPage(Integer page, Integer rows){
        Map<String,Object> map=new HashMap<String, Object>();
        Integer records=null;
        List<Album> list=null;
        /*if(_search){
            list=empService.queryBySearch(page*rows-rows,rows,searchString,searchField,searchOper);
            System.out.println(list);
            records=empService.queryBySearchNum(searchString,searchField,searchOper);
        }else {
            records=empService.queryNum();
            list=empService.queryAll(page*rows-rows,rows);
        }*/
        records=albumService.queryNum();
        list=albumService.queryByPage(page*rows-rows,rows);
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
    public Map cud(String oper,Album album,String[] id){
        Map hashMap=new HashMap();
        if (oper.equals("add")){
            album.setId(null);
            album.setCount(0);
            album.setCreate_date(new Date());
            albumService.add(album);
            hashMap.put("albumId",album.getId());
            hashMap.put("status",200);
        }
        if(oper.equals("edit")){
            album.setCover(null);
            hashMap.put("albumId",album.getId());
            hashMap.put("status",200);
            albumService.update(album);
        }
        if ("del".equals(oper)){
            List<String> ids=Arrays.asList(id);
            albumService.delete(ids);
            for (String str:ids){
                Chapter chapter = new Chapter();
                chapter.setAlbum_id(str);
                chapterService.deleteByAlbum_id(chapter);
            }
        }
        return hashMap;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile cover, String albumId,HttpServletRequest request) throws Exception {
        String realPath=request.getSession().getServletContext().getRealPath("/img");
        if (cover.getSize()!=0){
            File file=new File(realPath);
            if (!file.exists()){
                file.mkdirs();
            }
            String originalFilename=cover.getOriginalFilename();
            originalFilename=new Date().getTime()+"_"+originalFilename;
            cover.transferTo(new File(realPath,originalFilename));
            String http=request.getScheme();
            String loaclhost= InetAddress.getLocalHost().toString();
            int serverPort=request.getServerPort();
            String contextPath=request.getContextPath();
            String uri=http+"://"+loaclhost.split("/")[1]+":"+serverPort+contextPath+"/img/"+originalFilename;
            Album album=new Album();
            album.setId(albumId);
            album.setCover(uri);
            albumService.update(album);
        }

    }
    @RequestMapping("queryOne") //专辑详情
    public Map queryOne(String uid,String id){
        List<Album> albums = albumService.queryBySearch(new Album().setId(id));
        List<Chapter> chapters = chapterService.queryBySearch(new Chapter().setAlbum_id(id));
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("album",albums.get(0));
        map.put("chapter",chapters);
        return map;
    }
}
