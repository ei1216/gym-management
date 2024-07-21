import { defineStore } from 'pinia';
import { ref } from 'vue';

interface TokenState {
    token: string;
    setToken: (newToken: string) => void;
    removeToken: () => void;
}

// 定义store
export const useTokenStore = defineStore('token', () => {
    // 定义响应式变量token
    const token = ref<string>('');

    // 定义一个函数，修改token的值
    const setToken = (newToken: string) => {
        token.value = newToken;
    };

    // 定义删除token值的函数
    const removeToken = () => {
        token.value = '';
    };

    // 记住 要返回才能够使用
    return {
        token,
        setToken,
        removeToken,
    };

}, {
    persist: true, // 进行持久化存储（刷新页面后也不会丢失）
});
