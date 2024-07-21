/**
 * Course，课程实体
 */
export interface Course {

    /**
     * ID
     */
    id?: string;

    /**
     * 课程名称
     */
    name: string;

    /**
     * 课程描述
     */
    description: string;

    /**
     * 私教id
     */
    coachId: string;

    /**
     * 开始日期
     */
    startDate: string;

    /**
     * 结束日期
     */
    endDate: string;

    /**
     * 每周周几上课
     */
    daysOfWeek: string;

    /**
     * 价格
     */
    price: string;

}

/**
 * 课程分页条件查询实体
 */
export interface CourseQuery {
    /**
     * 课程名称
     */
    name?: string | null;

    /**
     * 页码
     */
    pageNo: number;

    /**
     * 每页条数
     */
    pageSize: number;

}

/**
 * 带有私教名称的课程
 */
export interface CourseWithCoachName extends Course {
    coachName?: string; // 可选属性，因为我们可能找不到所有课程的教练
}