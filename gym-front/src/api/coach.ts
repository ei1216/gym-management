import type {Coach, CoachQuery} from "@/types/coach";
import request from "@/request";

/**
 * 获取私教列表
 */
export function coachListService() {
    return request({
        url: "/coach",
        method: "get",
    })

}

/**
 * 添加私教
 * @param data
 */
export function coachAddService(data: Coach) {
    return request({
        url: "/coach",
        method: "post",
        data
    })

}


/**
 * 修改私教
 * @param data
 */
export function coachUpdateService(data: Coach) {
    return request({
        url: "/coach/update",
        method: "put",
        data
    })

}


/**
 * 删除私教
 * @param data
 */
export function coachDeleteService(data: Coach) {
    return request({
        url: "/coach?id=" + data.id,
        method: "delete",
    })

}


/**
 * 私教分页条件查询
 * @param params
 */
export function coachPageService(params: CoachQuery) {
    return request.get('/coach/page',{params: params})
}