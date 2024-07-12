package com.bequre.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "私教实体")
public class Coach {

    @NotNull
    @Schema(name = "id", description = "ID")
    private Integer id;

    @Schema(name = "name", description = "姓名")
    private String name;

    @NotNull
    @Schema(name = "phone", description = "电话")
    private String phone;

    @NotNull
    @Schema(name = "height", description = "身高")
    private Integer height;

    @NotNull
    @Schema(name = "weight", description = "体重")
    private Integer weight;

    @NotNull
    @Schema(name = "joinDate", description = "加入日期")
    private LocalDate joinDate;

    @NotNull
    @Schema(name = "salary", description = "薪水")
    private Integer salary;


}
