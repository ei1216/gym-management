package com.bequre.pojo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "会员分页条件查询实体")
public class MemberQuery extends PageQuery{

    @Schema(name = "name", description = "姓名")
    private String name;

    @Schema(name = "phone", description = "电话")
    private String phone;

    @Schema(name = "type", description = "会员类型")
    private String type;

}
