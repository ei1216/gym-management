package com.bequre.pojo.query;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "课程分页条件查询实体")
public class CourseQuery extends PageQuery{

    @Schema(name = "name", description = "课程名称")
    private String name;


}
