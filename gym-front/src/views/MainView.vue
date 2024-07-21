<script setup lang="ts">
import router from "@/router";
import {
  Avatar,
  CaretBottom,
  Collection,
  DocumentCopy,
  Setting,
  SwitchButton,
  User
} from "@element-plus/icons-vue";
import {ElMessageBox,ElMessage} from "element-plus";
import {useTokenStore} from "@/stores/token";

//创建token仓库
const tokenStore = useTokenStore()

//点击下拉菜单的条目
const handleCommand = (command: string) => {
  if (command === "logout"){
    //给用户提示确认框
    ElMessageBox.confirm(
        '是否退出当前账户',
        '温馨提示',
        {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning',
        }
    ).then(async () => {
      //确认退出登录
      //1. 清空pinia中的token和个人信息
      tokenStore.removeToken()
      //2. 跳转到登录页面
      await router.push('/login')
      ElMessage({
        type: 'success',
        message: '退出成功',
      })
    })

  }else {
    //路由跳转到相应页面（因为目前只写了一个退出登录，因此这个不会执行
    //router.push('/user/' + command)
  }

}


</script>

<template>

  <el-container class="layout-container">
    <!-- 左侧侧边栏 -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <!--      ep的菜单标签  -->
      <el-menu :default-active="$route.path" active-text-color="#2CA866" background-color="#232323"  text-color="#fff"
               router>

        <el-menu-item index="/equipment">
          <el-icon><Setting /></el-icon>
          <span>器材管理</span>
        </el-menu-item>

        <el-menu-item index="/coach">
          <el-icon><Avatar /></el-icon>
          <span>私教管理</span>
        </el-menu-item>

        <el-menu-item index="/member">
          <el-icon><User /></el-icon>
          <span>会员管理</span>
        </el-menu-item>

        <el-menu-item index="/course">
          <el-icon><Collection /></el-icon>
          <span>课程管理</span>
        </el-menu-item>

        <el-menu-item index="/courseRegistration">
          <el-icon><DocumentCopy /></el-icon>
          <span>课程报名管理</span>
        </el-menu-item>

      </el-menu>

    </el-aside>

    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div>欢迎您，管理员</div>
        <!--        下拉菜单-->
        <!--        command：条目被点击后会触发，在事件函数上可以声明一个变量，接收条目对应的指令-->
        <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar src="avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
          <template #dropdown>
            <el-dropdown-menu>
<!--              <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>-->
              <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>

      <!-- 中间区域 -->
      <el-main>
        <!--        <div style="width: 1290px; height: 570px;border: 1px solid red;">-->
        <!--          内容展示区-->
        <!--        </div>-->
        <router-view/>
      </el-main>

      <!-- 底部区域 -->
      <el-footer>2024-7-19 made by xiehaonian</el-footer>
    </el-container>

  </el-container>


</template>

<style lang="scss" scoped>

.layout-container {
  height: 100vh;

  .el-aside {
    background-color: rgba(35, 35, 35, 0.99);

    &__logo {
      height: 120px;
      background: url('@/assets/icon.png') no-repeat center / 80px auto;
    }

    .el-menu {
      border-right: none;
      background-color: rgba(35, 35, 35, 0.99);
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: rgba(44, 168, 102, 0.95);
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}


</style>