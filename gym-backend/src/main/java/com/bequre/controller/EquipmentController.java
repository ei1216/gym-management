package com.bequre.controller;


import com.bequre.pojo.Equipment;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.Result;
import com.bequre.pojo.query.EquipmentQuery;
import com.bequre.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "器材管理")
@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @Operation(summary = "新增器材")
    @PostMapping
    public Result save(@RequestBody Equipment equipment){
        //设置更新日期
        equipment.setUpdateDate(LocalDate.now());
        //判断是否存在过该器材
        Equipment e = equipmentService.getByName(equipment.getName());
        //执行对应逻辑
        if (e != null){
            equipmentService.addQuantity(equipment);
        }else{
            equipmentService.save(equipment);
        }
        return Result.success();
    }

    @Operation(summary = "修改器材")
    @PutMapping("/update")
    public Result update(@RequestBody Equipment equipment){
        equipmentService.updateById(equipment);
        return Result.success();
    }

    @Operation(summary = "删除器材")
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        equipmentService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "器材分页条件查询")
    @GetMapping("/page")
    public Result<PageDTO<Equipment>> page(@ParameterObject EquipmentQuery equipmentQuery){
        return Result.success(equipmentService.queryEquipmentPage(equipmentQuery));
    }



}
