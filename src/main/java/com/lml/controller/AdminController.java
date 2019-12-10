package com.lml.controller;

import com.lml.dingshi.ControllerTask;
import com.lml.entity.Admin;
import com.lml.security.SecurityCode;
import com.lml.security.SecurityImage;
import com.lml.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ControllerTask controllerTask;

    @ResponseBody
    @RequestMapping("security")
    public String security(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String securityCode= SecurityCode.getSecurityCode();
        HttpSession session=request.getSession();
        session.setAttribute("securityCode", securityCode);
        BufferedImage image= SecurityImage.createImage(securityCode);
        OutputStream out=response.getOutputStream();
        ImageIO.write(image, "png", out);
        return null;
    }

    @ResponseBody
    @RequestMapping("login")
    public String login(HttpServletRequest request, Admin admin, String securityCode) throws IOException {
        HttpSession session = request.getSession();
        String securityCode1= (String) session.getAttribute("securityCode");
        if (securityCode1.equals(securityCode)){
            //封装令牌
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
            //通过Util类获取subject主体
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(usernamePasswordToken);
                return null;
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return "账号或密码错误";
            }


            /*Admin admin1 = adminService.queryOne(admin.getUsername());
            if (admin1==null){
                return "用户名不存在";
            }else {
                if (admin.getPassword().equals(admin1.getPassword())){
                    controllerTask.run();
                    return null;
                }else {
                    return "密码错误";
                }
            }*/

        }
        return "验证码错误";


    }

    @RequestMapping("logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }
}
