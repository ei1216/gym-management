package com.bequre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bequre.pojo.Coach;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.query.CoachQuery;

public interface CoachService extends IService<Coach> {

    /**
     *  私教分页条件查询
     * @param coachQuery
     * @return
     */
    PageDTO<Coach> queryCoachPage(CoachQuery coachQuery);
}
