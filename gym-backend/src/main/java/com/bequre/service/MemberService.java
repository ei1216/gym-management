package com.bequre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bequre.pojo.Member;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.query.MemberQuery;

public interface MemberService extends IService<Member> {

    /**
     *  根据会员类型自动设置会员过期日期
     * @param member
     */
    void autoExpiryDate(Member member);

    /**
     *  会员分页条件查询
     * @param memberQuery
     * @return
     */
    PageDTO<Member> queryMemberPage(MemberQuery memberQuery);
}
