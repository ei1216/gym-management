package com.bequre.pojo.query;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "通用分页查询实体")
public class PageQuery {

    @Schema(name = "page", description = "页码")
    @NotNull    //不加的话在接口文档中的请求参数里会显示"是否必须--false"
    private Integer page;
    @Schema(name = "size", description = "每页条数")
    @NotNull
    private Integer size;

    /**
     *  将通用分页实体转成MybatisPlus的分页实体（还可以根据需求添加排序条件
     * @return
     * @param <T>
     */
    public <T> Page<T> toMpPage() {
        return new Page<>(page, size);
    }

}
