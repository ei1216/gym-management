<script setup lang="ts">
import {reactive, ref} from "vue";
import type {RegisterData} from "@/types/admin";
import router from "@/router";
import {ElMessage} from "element-plus";
import {adminRegisterService} from "@/api/admin";


//定义表单数据
const registerData: RegisterData = reactive({
  username: '',
  password: '',
  rePassword: '',
  phone: ''
})

//清除表单数据
const clearForm = () => {
  registerData.username = ''
  registerData.password = ''
  registerData.rePassword = ''
  registerData.phone = ''
}

//调用注册接口
const register = async () => {
  if (!registerData.username || !registerData.password || !registerData.rePassword){
    ElMessage.error("账号或密码不能为空")
    return
  }
  if (registerData.password !== registerData.rePassword){
    ElMessage.error("两次密码不一致")
    return
  }
  await adminRegisterService(registerData)
  ElMessage.success("注册成功")
  router.push('/login')
}


</script>

<template>
  <div class="register-page">
    <el-card class="register" style="max-width: 480px" shadow="always">

      <el-form
          ref="form"
          style="max-width: 600px"
          :model="registerData"
          label-width="auto"
          class="login-form"
      >
        <h2 class="title">欢迎注册管理员</h2>
        <el-form-item label="账号：" prop="username">
          <el-input v-model="registerData.username" style="width: 240px" />
        </el-form-item>

        <el-form-item label="密码："  prop="password">
          <el-input v-model="registerData.password" type="password" style="width: 240px"/>
        </el-form-item>

        <el-form-item label="确认密码："  prop="password">
          <el-input v-model="registerData.rePassword" type="password" style="width: 240px"/>
        </el-form-item>

        <el-form-item label="手机号："  prop="password">
          <el-input v-model="registerData.phone" style="width: 240px"/>
        </el-form-item>

        <div class="btn">
          <el-button size="small" @click="router.push('/login')" type="primary">←</el-button>
          <el-button size="large" @click="register" style="margin-left: 155px" round type="primary">注册</el-button>
          <el-button size="large" round @click="clearForm">重置</el-button>
        </div>

      </el-form>

    </el-card>

  </div>

</template>

<style lang="scss" scoped>

  .register-page{
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

    .register{


      border-radius: 20px;

    }

  }

</style>