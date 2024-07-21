import request from "@/request";
import type {CourseRegistration, CourseRegQuery, CrQuery} from "@/types/courseRegistration";




/**
 * 添加课程报名
 * @param data
 */
export function courseRegistrationAddService(data: CourseRegistration) {
    return request({
        url: "/courseRegistration",
        method: "post",
        data
    })

}

/**
 * 修改课程报名
 * @param data
 */
export function courseRegistrationUpdateService(data: CourseRegistration) {
    return request({
        url: "/courseRegistration/update",
        method: "put",
        data
    })

}

/**
 * 删除课程报名
 * @param data
 */
export function courseRegistrationDeleteService(data: CourseRegistration) {
    return request({
        url: "/courseRegistration?id=" + data.id,
        method: "delete",
    })

}


/**
 * 课程报名分页条件查询
 * @param params
 */
export function courseRegistrationPageService(params: CourseRegQuery) {

    return request.get('/courseRegistration/page',{params: params})

}