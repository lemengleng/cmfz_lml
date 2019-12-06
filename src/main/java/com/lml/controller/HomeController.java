package com.lml.controller;

import com.lml.entity.Album;
import com.lml.entity.Article;
import com.lml.entity.Banner;
import com.lml.service.AlbumService;
import com.lml.service.ArticleService;
import com.lml.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("home")
public class HomeController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @RequestMapping("queryFront")
    public Map queryFront(String uid,String type,String sub_type){

        List<Banner> listBanner = bannerService.queryBySearch(new Banner().setStatus("1"));
        List<Album> listAlbum = albumService.queryBySearch(new Album().setStatus("1"));
        List<Article> listArticle = articleService.queryBySearch(new Article().setStatus("1"));
        HashMap<Object, Object> map = new HashMap<>();
        if ("all".equals(type)){
            map.put("status",200);
            map.put("banner",listBanner);
            map.put("albums",listAlbum);
            map.put("articles",listArticle);
        }
        //音频
        if ("wen".equals(type)){
            map.put("status",200);
            map.put("banner",listBanner);
            map.put("albums",listAlbum);
        }
        //文章
        if ("si".equals(type)){
                
            if ("ssjy".equals(sub_type)){
                HashOperations<String, Object, Object> stringObjectObjectHashOperations = stringRedisTemplate.opsForHash();
                Object cmfz_gz = stringObjectObjectHashOperations.get("cmfz_gz", uid);
                List<Article> articleList=articleService.queryBySearch(new Article());
            }

            if ("ssjy".equals(sub_type)){
                List<Article> articleList=articleService.queryBySearch(new Article().setGuru_id("1"));
            }
            map.put("status",200);
            map.put("banner",listBanner);
            map.put("articles",listArticle);
        }

        return map;
    }
}
