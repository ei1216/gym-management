import request from "@/request";
import type {Member, MemberQuery} from "@/types/member";

/**
 * 获取会员列表
 */
export function memberListService() {
    return request({
        url: "/member",
        method: "get",
    })

}


/**
 * 添加会员
 * @param data
 */
export function memberAddService(data: Member) {
    return request({
        url: "/member",
        method: "post",
        data
    })

}

/**
 * 修改会员
 * @param data
 */
export function memberUpdateService(data: Member) {
    return request({
        url: "/member/update",
        method: "put",
        data
    })

}

/**
 * 删除会员
 * @param data
 */
export function memberDeleteService(data: Member) {
    return request({
        url: "/member?id=" + data.id,
        method: "delete",
    })

}


/**
 * 会员分页条件查询
 * @param params
 */
export function memberPageService(params: MemberQuery) {

    return request.get('/member/page',{params: params})

}