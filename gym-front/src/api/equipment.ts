import request from '@/request/index'
import type {Equipment, EquipmentQuery} from "@/types/equipment";

/**
 * 添加器材
 * @param data
 */
export function equipmentAddService(data: Equipment) {
    return request({
        /*headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },*/
        url: "/equipment",
        method: "post",
        data
    })

}

/**
 * 修改器材
 * @param data
 */
export function equipmentUpdateService(data: Equipment) {
    return request({
        /*headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },*/
        url: "/equipment/update",
        method: "put",
        data
    })

}

/**
 * 删除器材
 * @param data
 */
export function equipmentDeleteService(data: Equipment) {
    return request({
        url: "/equipment?id=" + data.id,
        method: "delete",
    })

}


/**
 * 器材分页条件查询
 * @param params
 */
export function equipmentPageService(params: EquipmentQuery) {

    return request.get('/equipment/page',{params: params})

}


