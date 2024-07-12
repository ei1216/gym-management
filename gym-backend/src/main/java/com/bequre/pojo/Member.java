package com.bequre.pojo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "会员实体")
public class Member {

    @NotNull
    @Schema(name = "id", description = "ID")
    private Integer id;

    @NotNull
    @Schema(name = "name", description = "姓名")
    private String name;

    @NotNull
    @Schema(name = "phone", description = "电话")
    private String phone;

    @NotNull
    @Schema(name = "membershipType", description = "会员类型")
    private String membershipType;

    @NotNull
    @Schema(name = "joinDate", description = "加入日期")
    private LocalDate joinDate;

    @NotNull
    @Schema(name = "expiryDate", description = "过期日期")
    private LocalDate expiryDate;

}
