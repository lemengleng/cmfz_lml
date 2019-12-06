package com.lml.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class HttpUtil {
    public static String getHttpUrl(MultipartFile file, HttpServletRequest request,String dir) throws Exception {
        String realPath=request.getSession().getServletContext().getRealPath(dir);
        File f=new File(realPath);
        if(!f.exists()){
            f.mkdirs();
        }
        String originalFilename=file.getOriginalFilename();
        originalFilename=new Date().getTime()+"_"+originalFilename;
        try {
            file.transferTo(new File(realPath,originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        String http=request.getScheme();
        String localHost=null;
        try {
            localHost= InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int serverPort=request.getServerPort();
        String contextPath=request.getContextPath();
        String uri=http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+dir+originalFilename;
        System.out.println(uri);
        return uri;
    }
}
