package com.bequre.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bequre.mapper.CoachMapper;
import com.bequre.pojo.Coach;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.query.CoachQuery;
import com.bequre.service.CoachService;
import org.springframework.stereotype.Service;

@Service
public class CoachServiceImpl extends ServiceImpl<CoachMapper, Coach> implements CoachService {


    /**
     *  私教分页条件查询
     * @param coachQuery
     * @return
     */
    public PageDTO<Coach> queryCoachPage(CoachQuery coachQuery) {
        //1. 构建分页条件
        Page<Coach> p = coachQuery.toMpPage();

        //2. 分页条件查询
        String name = coachQuery.getName();
        String phone = coachQuery.getPhone();
        Integer height = coachQuery.getHeight();
        Page<Coach> page = lambdaQuery()
                .eq(name != null, Coach::getName, name)
                .eq(phone != null, Coach::getPhone, phone)
                .eq(height != null, Coach::getHeight, height)
                .page(p);

        //3. 封装分页结果
        return new PageDTO<>(p.getTotal(),p.getPages(),p.getRecords());
    }
}
