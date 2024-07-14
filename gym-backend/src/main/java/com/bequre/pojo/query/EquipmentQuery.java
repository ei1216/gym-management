package com.bequre.pojo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "器材分页条件查询实体")
public class EquipmentQuery extends PageQuery{

    @Schema(name = "name", description = "器材名称")
    private String name;

    @Schema(name = "status", description = "器材状态")
    private String status;

}
