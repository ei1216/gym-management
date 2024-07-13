package com.bequre.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bequre.mapper.AdminMapper;
import com.bequre.pojo.Admin;
import com.bequre.service.AdminService;
import com.bequre.utils.JwtUtil;
import com.bequre.utils.Md5Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService {

    private final StringRedisTemplate redisTemplate;

    /**
     * 根据用户名查询管理员信息
     * @param username
     * @return
     */
    public Admin getByUsername(String username) {

        return lambdaQuery()
                .eq(Admin::getUsername, username)
                .one();
    }

    /**
     * 验证管理员登录，正确则保存信息且返回token，否则返回NULL
     *
     * @param username
     * @param password
     */
    public String validateLogin(String username, String password) {
        //执行到这个方法已经说明了用户名存在
        Admin admin = getByUsername(username);
        //加密处理密码
        String md5Pwd = Md5Util.getMD5String(password);
        if (admin.getPassword().equals(md5Pwd)){
            //1. 构造生成token的数据源
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", admin.getId());
            claims.put("username", admin.getUsername());
            //2. 生成token
            String token = JwtUtil.genToken(claims);
            //3. 将token存入redis中，保持过期时间与token的过期时间一致
            redisTemplate.opsForValue().set(token, token,2, TimeUnit.HOURS);
            //4. 返回token
            return token;
        }
        return null;

    }



}
