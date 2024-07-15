package com.bequre.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bequre.mapper.CourseMapper;
import com.bequre.pojo.Course;
import com.bequre.pojo.Member;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.query.CourseQuery;
import com.bequre.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    /**
     *  课程分页条件查询
     * @param courseQuery
     * @return
     */
    public PageDTO<Course> queryCoursePage(CourseQuery courseQuery) {
        //1. 构建分页条件
        Page<Course> p = courseQuery.toMpPage();

        //2. 分页条件查询
        String name = courseQuery.getName();
        Page<Course> page = lambdaQuery()
                .eq(name != null, Course::getName, name)
                .page(p);

        //3. 封装分页结果
        return new PageDTO<>(page.getTotal(),page.getPages(),page.getRecords());
    }
}
