package com.bequre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bequre.pojo.CourseRegistrations;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.query.CourseRegistrationQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiehaonian
 * @since 2024-07-15
 */
public interface CourseRegistrationsService extends IService<CourseRegistrations> {

    /**
     * 课程报名分页条件查询
     * @param query
     * @return
     */
    PageDTO<CourseRegistrations> queryCourseRegistrationPage(CourseRegistrationQuery query);
}
