package com.bequre.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bequre.mapper.MemberMapper;
import com.bequre.pojo.Coach;
import com.bequre.pojo.Member;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.query.MemberQuery;
import com.bequre.service.MemberService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    /**
     *  根据会员类型自动设置会员过期日期
     * @param member
     */
    public void autoExpiryDate(Member member) {
        String type = member.getType();
        switch(type) {
            case "月卡会员":
                member.setExpiryDate(LocalDate.now().plusMonths(1));
                break;
            case "季卡会员":
                member.setExpiryDate(LocalDate.now().plusMonths(3));
                break;
            case "年卡会员":
                member.setExpiryDate(LocalDate.now().plusYears(1));
                break;
        }
    }

    /**
     *  会员分页条件查询
     * @param memberQuery
     * @return
     */
    public PageDTO<Member> queryMemberPage(MemberQuery memberQuery) {
        //1. 构建分页条件
        Page<Member> p = memberQuery.toMpPage();

        //2. 分页条件查询
        String name = memberQuery.getName();
        String phone = memberQuery.getPhone();
        String type = memberQuery.getType();
        Page<Member> page = lambdaQuery()
                .eq(name != null, Member::getName, name)
                .eq(phone != null, Member::getPhone, phone)
                .eq(type != null, Member::getType, type)
                .page(p);

        //3. 封装分页结果
        return new PageDTO<>(page.getTotal(),page.getPages(),page.getRecords());

    }


}
