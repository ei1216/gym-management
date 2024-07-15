package com.bequre.pojo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "课程报名分页条件查询实体")
public class CourseRegistrationQuery extends PageQuery{

    @Schema(name = "courseId", description = "课程id")
    private Integer courseId;

    @Schema(name = "memberId", description = "会员id")
    private Integer memberId;

}
