package com.bequre.controller;

import com.bequre.pojo.Equipment;
import com.bequre.pojo.Member;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.Result;
import com.bequre.pojo.query.EquipmentQuery;
import com.bequre.pojo.query.MemberQuery;
import com.bequre.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "会员管理")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "新增会员")
    @PostMapping
    public Result save(@RequestBody Member member){
        member.setJoinDate(LocalDate.now());
        memberService.autoExpiryDate(member);
        memberService.save(member);
        return Result.success();
    }

    @Operation(summary = "修改会员")
    @PutMapping("/update")
    public Result update(@RequestBody Member member){
        memberService.autoExpiryDate(member);
        memberService.updateById(member);
        return Result.success();
    }

    @Operation(summary = "删除会员")
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        memberService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "会员分页条件查询")
    @GetMapping("/page")
    public Result<PageDTO<Member>> page(@ParameterObject MemberQuery memberQuery){
        return Result.success(memberService.queryMemberPage(memberQuery));
    }


}
