package com.bequre.controller;

import com.bequre.pojo.Admin;
import com.bequre.pojo.Result;
import com.bequre.service.AdminService;
import com.bequre.utils.Md5Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@Tag(name = "管理员管理")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public Result login(String username, String password) {
        //1. 查找用户名
        Admin admin = adminService.getByUsername(username);
        if (admin == null) {
            return Result.error("用户不存在");
        }
        //2. 验证账密并获取token
        String token = adminService.validateLogin(username, password);
        if (token == null){
            return Result.error("密码错误");
        }
        //3. 返回token
        return Result.success(token);
    }

    @Operation(summary = "管理员注册")
    @PostMapping("/register")
    public Result register(String username, String password, String phone) {
        //1. 验证用户名是否已存在
        Admin adm = adminService.getByUsername(username);
        if (adm != null) {
            return Result.error("用户名已存在");
        }
        //2. 封装对象
        Admin admin = Admin.builder()
                .username(username)
                .password(Md5Util.getMD5String(password))
                .phone(phone)
                .createDate(LocalDate.now())
                .build();
        //3. 注册
        adminService.save(admin);
        return Result.success();
    }




}
