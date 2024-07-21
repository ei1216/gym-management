/**
 * CourseRegistration，课程报名实体
 */
export interface CourseRegistration {

    /**
     * ID
     */
    id?: string;

    /**
     * 课程id
     */
    courseId: string;

    /**
     * 课程名称
     */
    courseName?: string;

    /**
     * 会员id
     */
    memberId: string;

    /**
     * 会员名称
     */
    memberName?: string;

    /**
     * 会员注册课程日期
     */
    registrationDate?: string;
}

/**
 *  课程报名分页条件查询实体
 */
export interface CourseRegQuery {
    /**
     * 页码
     */
    pageNo: number;

    /**
     * 每页条数
     */
    pageSize: number;

    /**
     * 课程id
     */
    courseId?: string | null;

    /**
     * 会员id
     */
    memberId?: string | null;


}

/**
 * 带有课程名称和会员名称的课程报名实体
 */
export interface CourseRegistrationsWithNames extends CourseRegistration {
    courseName?: string;
    memberName?: string;
}