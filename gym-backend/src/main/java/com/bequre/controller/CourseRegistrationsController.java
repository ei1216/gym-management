package com.bequre.controller;


import com.bequre.pojo.CourseRegistrations;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.Result;
import com.bequre.pojo.query.CourseQuery;
import com.bequre.pojo.query.CourseRegistrationQuery;
import com.bequre.service.CourseRegistrationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiehaonian
 * @since 2024-07-15
 */
@Tag(name = "课程报名管理")
@RestController
@RequestMapping("/courseRegistration-registrations")
@RequiredArgsConstructor
public class CourseRegistrationsController {

    private final CourseRegistrationsService courseService;

    @Operation(summary = "新增课程报名")
    @PostMapping
    public Result save(@RequestBody CourseRegistrations courseRegistration){
        courseRegistration.setRegistrationDate(LocalDate.now());
        courseService.save(courseRegistration);
        return Result.success();
    }

    @Operation(summary = "修改课程报名")
    @PutMapping("/update")
    public Result update(@RequestBody CourseRegistrations courseRegistration){
        courseService.updateById(courseRegistration);
        return Result.success();
    }

    @Operation(summary = "删除课程报名")
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        courseService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "课程报名分页条件查询")
    @GetMapping("/page")
    public Result<PageDTO<CourseRegistrations>> page(@ParameterObject CourseRegistrationQuery query){
        return Result.success(courseService.queryCourseRegistrationPage(query));
    }



}
