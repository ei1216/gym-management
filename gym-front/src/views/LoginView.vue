<script setup lang="ts">
import {reactive} from 'vue';
import type {LoginData} from "@/types/admin";
import {adminLoginService} from "@/api/admin";
import {ElMessage} from "element-plus";
import {useTokenStore} from "@/stores/token";
import router from "@/router";

//定义表单验证规则
/*const rules = {
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur'}
  ]
}*/

//定义表单数据
const data: LoginData = reactive({
  username: '',
  password: '',
});

//创建token仓库
const tokenStore = useTokenStore()

//调用登录接口
const login = async () => {
  if (!data.username || !data.password){
    ElMessage.error("账号或密码不能为空")
    return
  }
  let result = await adminLoginService(data)
  tokenStore.setToken(result.data)
  ElMessage.success("登陆成功")
  router.push('/')

}

</script>

<template>
  <div class="login-page">

    <el-form
        ref="form"
        style="max-width: 600px"
        :model="data"
        label-width="auto"
        class="login-form"
    >
      <h1 class="title">健身房管理系统</h1>
      <el-form-item label="账号：" prop="username">
        <el-input v-model="data.username" style="width: 240px" />
      </el-form-item>

      <el-form-item label="密码：" style="margin-bottom: 30px" prop="password">
        <el-input v-model="data.password" type="password" style="width: 240px"/>
      </el-form-item>

      <div class="btn">
        <el-button size="large" @click="login" style="width: 100px" round type="primary">登录</el-button>
        <el-button size="large" @click="router.push('/register')" style="width: 100px" round type="primary">注册</el-button>
      </div>

    </el-form>

  </div>
</template>

<style lang="scss" scoped>

  .login-page {
    /*背景处理*/
    height: 100vh;
    margin: 0;
    padding: 0;
    background-image: url('@/assets/bg.jpg');
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;

    /* 居中处理 */
    display: flex;
    justify-content: center; /* 水平居中 */
    align-items: center;     /* 垂直居中 */

    .title {
      text-align: center;
      margin-bottom: 25px;
    }

    .login-form {
      padding: 25px;
      background-color: white;
      border-radius: 20px;
    }

    .btn {
      display: flex;
      justify-content: flex-end;  /*居右*/

    }
  }

</style>



