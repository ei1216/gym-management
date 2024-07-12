package com.bequre.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;


@Schema(description = "管理员实体")
@Data
public class Admin {

    @NotNull
    @Schema(name = "id", description = "ID")
    private Integer id;

    @Schema(name = "username", description = "用户名")
    private String username;

    @JsonIgnore     //在springMvc把user对象转成json格式数据时，忽略转换密码这一属性
    @Schema(name = "password", description = "密码")
    private String password;

    @NotNull
    @Schema(name = "phone", description = "电话")
    private String phone;

    @Schema(name = "createDate", description = "创建日期")
    private LocalDate createDate;

}
