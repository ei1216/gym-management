import request from "@/request";
import type {Course, CourseQuery} from "@/types/course";

/**
 * 获取课程列表
 */
export function courseListService() {
    return request({
        url: "/course",
        method: "get",
    })

}

/**
 * 添加课程
 * @param data
 */
export function courseAddService(data: Course) {
    return request({
        url: "/course",
        method: "post",
        data
    })

}

/**
 * 修改课程
 * @param data
 */
export function courseUpdateService(data: Course) {
    return request({
        url: "/course/update",
        method: "put",
        data
    })

}

/**
 * 删除课程
 * @param data
 */
export function courseDeleteService(data: Course) {
    return request({
        url: "/course?id=" + data.id,
        method: "delete",
    })

}


/**
 * 课程分页条件查询
 * @param params
 */
export function coursePageService(params: CourseQuery) {

    return request.get('/course/page',{params: params})

}