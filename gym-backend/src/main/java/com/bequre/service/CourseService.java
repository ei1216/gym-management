package com.bequre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bequre.pojo.Course;
import com.bequre.pojo.Member;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.query.CourseQuery;

public interface CourseService extends IService<Course> {

    /**
     *  课程分页条件查询
     * @param courseQuery
     * @return
     */
    PageDTO<Course> queryCoursePage(CourseQuery courseQuery);
}
