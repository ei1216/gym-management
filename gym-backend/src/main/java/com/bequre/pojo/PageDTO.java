package com.bequre.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Schema(name = "分页结果实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T>{
    @Schema(name = "总条数")
    private Long total;
    @Schema(name = "总页数")
    private Long pages;
    @Schema(name = "当前页数据集合")
    private List<T> list;

}
