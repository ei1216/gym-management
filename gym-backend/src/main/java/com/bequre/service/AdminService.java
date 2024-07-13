package com.bequre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bequre.pojo.Admin;


public interface AdminService extends IService<Admin> {

    /**
     * 验证管理员登录，正确则保存信息且返回token，否则返回NULL
     * @param username
     * @param password
     */
    String validateLogin(String username, String password);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    Admin getByUsername(String username);

}
