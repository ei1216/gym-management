package com.bequre.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bequre.mapper.CourseRegistrationsMapper;
import com.bequre.pojo.CourseRegistration;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.query.CourseRegistrationQuery;
import com.bequre.service.CourseRegistrationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiehaonian
 * @since 2024-07-15
 */
@Service
public class CourseRegistrationServiceImpl extends ServiceImpl<CourseRegistrationsMapper, CourseRegistration> implements CourseRegistrationService {

    /**
     * 课程报名分页条件查询
     * @param query
     * @return
     */
    public PageDTO<CourseRegistration> queryCourseRegistrationPage(CourseRegistrationQuery query) {
        //1. 构建分页条件
        Page<CourseRegistration> p = query.toMpPage();

        //2. 分页条件查询
        Integer courseId = query.getCourseId();
        Integer memberId = query.getMemberId();
        Page<CourseRegistration> page = lambdaQuery()
                .eq(courseId != null, CourseRegistration::getCourseId, courseId)
                .eq(memberId != null, CourseRegistration::getMemberId, memberId)
                .page(p);

        //3. 封装分页结果
        return new PageDTO<>(page.getTotal(),page.getPages(),page.getRecords());
    }


}
