package com.bequre.controller;


import com.bequre.pojo.Course;
import com.bequre.pojo.Member;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.Result;
import com.bequre.pojo.query.CourseQuery;
import com.bequre.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


@Tag(name = "课程管理")
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @Operation(summary = "新增课程")
    @PostMapping
    public Result save(@RequestBody Course course){
        courseService.save(course);
        return Result.success();
    }

    @Operation(summary = "修改课程")
    @PutMapping("/update")
    public Result update(@RequestBody Course course){
        courseService.updateById(course);
        return Result.success();
    }

    @Operation(summary = "删除课程")
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        courseService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "课程分页条件查询")
    @GetMapping("/page")
    public Result<PageDTO<Course>> page(@ParameterObject CourseQuery courseQuery){
        return Result.success(courseService.queryCoursePage(courseQuery));
    }

}
