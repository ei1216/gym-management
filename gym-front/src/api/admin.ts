import request from '@/request/index'
import type {LoginData} from "@/types/admin";

/**
 * 管理员登录
 * @param data
 */
export function adminLoginService(data: LoginData) {
    return request({
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        url: "/admin/login",
        method: "post",
        data
    })

}

/**
 * 管理员注册
 * @param data      由于注册接口的参数和登录接口是一样的，因此要用LoginData
 */
export function adminRegisterService(data: LoginData) {
    return request({
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        url: "/admin/register",
        method: "post",
        data
    })

}