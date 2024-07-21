/**
 * Coach，私教实体
 * 某些属性用string是便于前端处理，不用管校验（因为后端已经完善了）
 */
export interface Coach {
    /**
     * ID
     * 添加的请求不需要这个参数
     */
    id?: string;
    /**
     * 姓名
     */
    name: string;
    /**
     * 电话
     */
    phone: string;
    /**
     * 身高
     */
    height: string;
    /**
     * 体重
     */
    weight: string;
    /**
     * 加入日期
     */
    joinDate?: Date;
    /**
     * 薪水
     */
    salary: string;
}

/**
 * 私教分页条件查询实体
 */
export interface CoachQuery {
    /**
     * 姓名
     */
    name?: string | null;

    /**
     * 电话
     */
    phone?: string | null;

    /**
     * 身高
     */
    height?: string | null;

    /**
     * 页码
     */
    pageNo: number;

    /**
     * 每页条数
     */
    pageSize: number;
}

