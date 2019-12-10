package com.lml.realm;

import com.lml.dao.AdminDao;
import com.lml.entity.Admin;
import com.lml.entity.Resources;
import com.lml.entity.Role;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private AdminDao adminDao;

    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取主身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        //通过主身份信息查询数据库
        Admin admin = adminDao.queryAdminInfo(primaryPrincipal);
        ArrayList roles = new ArrayList();
        ArrayList resources = new ArrayList();
        List<Role> role = admin.getRoles();
        for (Role role1 : role) {
            roles.add(role1.getRole_name());
            for (Resources resources1 : role1.getResources()) {
                resources.add(resources1.getResource_name());
            }
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(resources);
        return authorizationInfo;
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        Admin admin = new Admin();
        admin.setUsername(principal);
        Admin admin1 = adminDao.selectOne(admin);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin1.getUsername(), admin1.getPassword(), this.getName());

        return authenticationInfo;
    }
}
