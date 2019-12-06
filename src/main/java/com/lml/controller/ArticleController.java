package com.lml.controller;

import com.lml.entity.Album;
import com.lml.entity.Article;
import com.lml.entity.Chapter;
import com.lml.service.ArticleService;
import com.lml.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ArticleService articleService;
    @RequestMapping("queryByPage")
    public Map<String,Object> queryByPage(Integer page, Integer rows){
        Map<String,Object> map=new HashMap<String, Object>();
        Integer records=null;
        List<Article> list=null;
        /*if(_search){
            list=empService.queryBySearch(page*rows-rows,rows,searchString,searchField,searchOper);
            System.out.println(list);
            records=empService.queryBySearchNum(searchString,searchField,searchOper);
        }else {
            records=empService.queryNum();
            list=empService.queryAll(page*rows-rows,rows);
        }*/
        records=articleService.queryNum();
        list=articleService.qyeryByPage(page*rows-rows,rows);
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
    public Map cud(String oper,Article article,String[] id){
        Map hashMap=new HashMap();
        if (oper.equals("add")){
            article.setId(null);
            article.setCreate_date(new Date());
            article.setPublish_date(new Date());
            articleService.add(article);
            hashMap.put("albumId",article.getId());
            hashMap.put("status",200);
        }
        if(oper.equals("edit")){
            article.setImg(null);
            hashMap.put("albumId",article.getId());
            hashMap.put("status",200);
            articleService.updateById(article);
        }
        if ("del".equals(oper)){
            articleService.deleteByList(id);
        }
        return hashMap;
    }
    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpServletRequest request){
        Map hashMap=new HashMap();
        String dir="/upload/articleImg/";
        try {
            String httpUrl= HttpUtil.getHttpUrl(imgFile,request,dir);
            hashMap.put("error",0);
            hashMap.put("url",httpUrl);
        } catch (Exception e) {
            hashMap.put("error",1);
            hashMap.put("message","上传错误");
            e.printStackTrace();
        }
        return hashMap;
    }
    @RequestMapping("showAllImgs")
    public Map showAllImgs(HttpServletRequest request){
        String realPath=request.getSession().getServletContext().getRealPath("/upload/articleImg/");
        HashMap hashMap=new HashMap();
        ArrayList arrayList=new ArrayList();
        File file=new File(realPath);
        File[] files=file.listFiles();
        for (File file1:files){
            HashMap fileMap=new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String extension= FilenameUtils.getExtension(file1.getName());
            fileMap.put("filetype",extension);
            fileMap.put("filename",file1.getName());
            String s=file1.getName().split("_")[0];
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format=simpleDateFormat.format(new Date(Long.valueOf(s)));
            fileMap.put("datatime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        hashMap.put("total_count",arrayList.size());
        hashMap.put("current_url",request.getContextPath()+"/upload/articleImg/");
        return hashMap;
    }
    @RequestMapping("insertArticle")
    public void insertArticle(MultipartFile articleImg,Article article,HttpServletRequest request){
        article.setId(null);
        article.setCreate_date(new Date());
        article.setPublish_date(new Date());
        try {
            String url=HttpUtil.getHttpUrl(articleImg,request,"/upload/articleCover/");
            article.setImg(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        articleService.add(article);
    }
    @RequestMapping("updateArticle")
    public void updateArticle(MultipartFile articleImg,Article article,HttpServletRequest request){
        if (articleImg.getSize()!=0){
            try {
                String url=HttpUtil.getHttpUrl(articleImg,request,"/upload/articleCover/");
                article.setImg(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        articleService.updateById(article);
    }

    @RequestMapping("queryOne")
    public Map queryOne(String uid,String id){
        List<Article> articleList = articleService.queryBySearch(new Article().setId(uid));
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("article",articleList.get(0));
        return map;
    }
}
