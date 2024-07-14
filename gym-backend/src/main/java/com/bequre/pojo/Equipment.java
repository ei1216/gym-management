package com.bequre.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "设备实体")
public class Equipment {

    @NotNull
    @Schema(name = "id", description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotNull
    @Schema(name = "name", description = "名称")
    private String name;

    @NotNull
    @Schema(name = "quantity", description = "数量")
    private Integer quantity;

    @NotNull
    @Schema(name = "status", description = "状态")
    private String status;

    @NotNull
    @Schema(name = "updateDate", description = "更新日期")
    private LocalDate updateDate;

    @NotNull
    @Schema(name = "notes", description = "备注")
    private String notes;


}
