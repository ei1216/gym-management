/**
 * Member，会员实体
 */
export interface Member {

    /**
     * ID
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
     * 会员类型
     */
    type: string;

    /**
     * 加入日期
     */
    joinDate?: string;

    /**
     * 过期日期
     */
    expiryDate?: string;

}

/**
 * 会员分页条件查询实体
 */
export interface MemberQuery {
    /**
     * 页码
     */
    pageNo: number;

    /**
     * 每页条数
     */
    pageSize: number;

    /**
     * 姓名
     */
    name?: string | null;

    /**
     * 电话
     */
    phone?: string | null;

    /**
     * 会员类型
     */
    type?: string | null;

}