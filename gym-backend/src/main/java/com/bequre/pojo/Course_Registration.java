package com.bequre.pojo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema (description = "课程报名实体")
public class Course_Registration {

    @NotNull
    @Schema(name = "id", description = "ID")
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

    @NotNull
    @Schema(name = "status", description = "注册状态")
    private String status;

}
