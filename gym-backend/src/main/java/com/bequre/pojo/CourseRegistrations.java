package com.bequre.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema (description = "课程报名实体")
@TableName("course_registrations")
public class CourseRegistrations {

    @NotNull
    @Schema(name = "id", description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotNull
    @Schema(name = "memberId", description = "会员id")
    private Integer memberId;

    @NotNull
    @Schema(name = "courseId", description = "课程id")
    private Integer courseId;

    @NotNull
    @Schema(name = "registrationDate", description = "会员注册课程日期")
    private LocalDate registrationDate;


}
