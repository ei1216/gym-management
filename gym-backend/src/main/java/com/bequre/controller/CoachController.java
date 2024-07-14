package com.bequre.controller;

import com.bequre.pojo.Coach;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.Result;
import com.bequre.pojo.query.CoachQuery;
import com.bequre.service.CoachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Tag(name = "私教管理")
@RestController
@RequestMapping("/coach")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @Operation(summary = "新增私教")
    @PostMapping
    public Result add(@RequestBody Coach coach) {
        coach.setJoinDate(LocalDate.now());
        coachService.save(coach);
        return Result.success();
    }

    @Operation(summary = "修改私教")
    @PutMapping("/update")
    public Result update(@RequestBody Coach coach) {
        coachService.updateById(coach);
        return Result.success();
    }

    @Operation(summary = "删除私教")
    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        coachService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "私教分页条件查询")
    @GetMapping("/page")
    public Result<PageDTO<Coach>> page(@ParameterObject CoachQuery coachQuery){
        return Result.success(coachService.queryCoachPage(coachQuery));
    }


}
