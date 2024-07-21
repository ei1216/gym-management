import axios from "axios";
import {useTokenStore} from "@/stores/token.js"
import {ElMessage} from "element-plus";
import router from "@/router";

// 创建axios实例
const instance = axios.create({
    baseURL: '/api',
    timeout: 5000,
})



//添加请求拦截器
instance.interceptors.request.use(config => {

    //获取token仓库
    const tokenStore = useTokenStore()
    //判断token是否存在
    if (tokenStore.token){
        //如果token存在的话，添加token
        config.headers.Authorization = tokenStore.token
    }
    //将config返回
    return config

}, error => {
    return Promise.reject(error)
})


//添加响应拦截器
instance.interceptors.response.use(
    result=>{
        //判断响应数据的状态码（后端规定了只有0和1）
        if (result.data.code === 0){
            return result.data;
        }
        //ElMessage.error(result.data.message ? result.data.message : "操作失败")
        if (result.data.message.length > 15){
            ElMessage.error( "不好意思，出了点问题...")
        }else {
            ElMessage.error(result.data.message)
        }
        //异步操作的状态转换为失败
        return Promise.reject(result.data)
    },
    err=>{
        //判断响应状态码（为啥是401？
        if (err.response.status === 401){
            //若为401，则说明未登录
            ElMessage.error("请先登录！")
            router.push('/login')
        }else {
            ElMessage.error("服务器异常")
        }

        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance