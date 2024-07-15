package com.bequre.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;


@Data
@Schema(description = "课程实体")
@TableName("courses")
public class Course {

    @NotNull
    @Schema(name = "id", description = "ID")
    private Integer id;

    @NotNull
    @Schema(name = "name", description = "课程名称")
    private String name;

    @NotNull
    @Schema(name = "description", description = "课程描述")
    private String description;

    @NotNull
    @Schema(name = "coachId", description = "私教id")
    private Integer coachId;

    @NotNull
    @Schema(name = "startDate", description = "开始日期")
    private LocalDate startDate;

    @NotNull
    @Schema(name = "endDate", description = "结束日期")
    private LocalDate endDate;

    @NotNull
    @Schema(name = "daysOfWeek", description = "每周周几上课")
    private String daysOfWeek;

    @NotNull
    @Schema(name = "price", description = "价格")
    private Float price;



}
