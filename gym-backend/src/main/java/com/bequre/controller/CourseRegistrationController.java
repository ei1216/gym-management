package com.bequre.controller;

import com.bequre.pojo.CourseRegistration;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.Result;
import com.bequre.pojo.query.CourseRegistrationQuery;
import com.bequre.service.CourseRegistrationService;
import com.bequre.service.CourseService;
import com.bequre.service.MemberService;
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
@RequestMapping("/courseRegistration")
@RequiredArgsConstructor
public class CourseRegistrationController {

    private final CourseRegistrationService courseRegistrationService;

    private final CourseService courseService;

    private final MemberService memberService;

    @Operation(summary = "新增课程报名")
    @PostMapping
    public Result save(@RequestBody CourseRegistration courseRegistration){
        String courseName = courseService.getById(courseRegistration.getCourseId()).getName();
        String memberName = memberService.getById(courseRegistration.getMemberId()).getName();
        courseRegistration.setCourseName(courseName);
        courseRegistration.setMemberName(memberName);
        courseRegistration.setRegistrationDate(LocalDate.now());
        courseRegistrationService.save(courseRegistration);
        return Result.success();
    }

    @Operation(summary = "修改课程报名")
    @PutMapping("/update")
    public Result update(@RequestBody CourseRegistration courseRegistration){
        courseRegistrationService.updateById(courseRegistration);
        return Result.success();
    }

    @Operation(summary = "删除课程报名")
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        courseRegistrationService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "课程报名分页条件查询")
    @GetMapping("/page")
    public Result<PageDTO<CourseRegistration>> page(@ParameterObject CourseRegistrationQuery query){
        return Result.success(courseRegistrationService.queryCourseRegistrationPage(query));
    }



}
