package com.bequre.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "会员实体")
@TableName("members")
public class Member {

    @NotNull
    @Schema(name = "id", description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotNull
    @Schema(name = "name", description = "姓名")
    private String name;

    @NotNull
    @Schema(name = "phone", description = "电话")
    private String phone;

    @NotNull
    @Schema(name = "type", description = "会员类型")
    private String type;

    @NotNull
    @Schema(name = "joinDate", description = "加入日期")
    private LocalDate joinDate;

    @NotNull
    @Schema(name = "expiryDate", description = "过期日期")
    private LocalDate expiryDate;

}
