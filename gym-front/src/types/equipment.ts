/**
 * Equipment，设备实体
 */
export interface Equipment {
    /**
     * ID
     * 添加的请求不需要这个参数
     */
    id?: number;
    /**
     * 名称
     */
    name: string;
    /**
     * 数量   改成string好处理一些
     */
    quantity: string;
    /**
     * 状态
     */
    status: string;
    /**
     * 更新日期
     */
    updateDate?: Date;
    /**
     * 备注
     */
    notes: string;
    //[property: string]: any;
}

/**
 * 器材分页条件查询实体
 */
export interface EquipmentQuery {

    /**
     * 页码
     */
    pageNo: number;
    /**
     * 每页条数
     */
    pageSize: number;
    /**
     * 器材名称
     */
    name?: string | null;
    /**
     * 器材状态
     */
    status?: string | null;

}