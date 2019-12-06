package com.lml.controller;

import com.lml.entity.Album;
import com.lml.entity.Chapter;
import com.lml.service.AlbumService;
import com.lml.service.ChapterService;
import com.lml.util.HttpUtil;
import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;
    @RequestMapping("queryByPage")
    public Map<String,Object> queryByPage(Integer page, Integer rows,String album_id){
        Map<String,Object> map=new HashMap<String, Object>();
        Integer records=null;
        List<Chapter> list=null;
        /*if(_search){
            list=empService.queryBySearch(page*rows-rows,rows,searchString,searchField,searchOper);
            System.out.println(list);
            records=empService.queryBySearchNum(searchString,searchField,searchOper);
        }else {
            records=empService.queryNum();
            list=empService.queryAll(page*rows-rows,rows);
        }*/
        Chapter chapter=new Chapter();
        chapter.setAlbum_id(album_id);
        records=chapterService.queryNum(chapter);
        list=chapterService.queryByPage(chapter,page*rows-rows,rows);
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
    public Map cud(String oper,Chapter chapter,String[] id){
        System.out.println(chapter);
        Map hashMap=new HashMap();
        if (oper.equals("add")){
            chapter.setId(null);
            chapter.setCreate_time(new Date());
            chapterService.add(chapter);
            Album album = new Album();
            album.setId(chapter.getAlbum_id());
            Integer num=albumService.queryById(album).getCount();
            album.setCount(num+1);
            System.out.println(album);
            albumService.update(album);//专辑集数+1
            hashMap.put("chapterId",chapter.getId());
            hashMap.put("status",200);
        }
        if(oper.equals("edit")){
            chapter.setUrl(null);
            hashMap.put("chapterId",chapter.getId());
            hashMap.put("status",200);
            chapterService.update(chapter);
        }
        if ("del".equals(oper)){
            List<String> ids=Arrays.asList(id);
            Album album = new Album();
            album.setId(chapter.getAlbum_id());
            Integer num=albumService.queryById(album).getCount();
            album.setCount(num-id.length);//专辑集数-删除的数量
            albumService.update(album);
            chapterService.delete(ids);
        }
        return hashMap;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile url, String chapterId, HttpServletRequest request) throws Exception {
        if(url.getSize()!=0){
            String uri= HttpUtil.getHttpUrl(url,request,"/img/");
            //获取文件名
            String[] split=uri.split("/");
            String s=split[split.length-1];//文件名
            String realPath=request.getSession().getServletContext().getRealPath("/img");
            File file=new File(realPath,s);
            long length=file.length();
            long size=length/1024/1024;
            MP3File read= (MP3File) AudioFileIO.read(file);
            MP3AudioHeader mp3AudioHeader = read.getMP3AudioHeader();
            int trackLength = mp3AudioHeader.getTrackLength();
            String min=trackLength/60+"分";
            String sed=trackLength%60+"秒";
            Chapter chapter=new Chapter();
            chapter.setId(chapterId);
            chapter.setUrl(uri);
            chapter.setTime(min+sed);
            chapter.setSize((double) size);
            chapterService.update(chapter);
        }

    }
    @RequestMapping("down")
    public void down(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String realPath=request.getSession().getServletContext().getRealPath("/img");
        String[] split=url.split("/");
        String s=split[split.length-1];//文件名
        File file=new File(realPath,s);
        FileInputStream is = new FileInputStream(file);
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(s,"UTF-8"));
        ServletOutputStream os = response.getOutputStream();
        IOUtils.copy(is,os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }
}
