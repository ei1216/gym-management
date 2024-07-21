<script setup lang="ts">
import {Delete, EditPen} from "@element-plus/icons-vue";
import {reactive, ref, watch} from "vue";
import {useTokenStore} from "@/stores/token";
import {ElMessage, ElMessageBox} from "element-plus";
import {coachAddService, coachDeleteService, coachPageService, coachUpdateService} from "@/api/coach";
import type {Coach} from "@/types/coach";

//搜索条件-私教名称
const name = ref('')
//搜索条件-私教电话
const phone = ref('')
//搜索条件-私教身高
const height = ref('')

//私教列表数据模型
const coaches = ref([])

//分页条数据模型
const pageNo = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//抽屉的标题
const drawerTitle = ref('')
//控制抽屉是否显示
const visibleDrawer = ref(false)
//抽屉出来的方向
const direction = ref('ltr')


//当每页条数发生了变化，调用此函数
const onSizeChange = (size: number) => {
  pageSize.value = size
  coachList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num: number) => {
  pageNo.value = num
  coachList()
}

/**
 * 获取私教列表数据
 */
const coachList = async () => {
  //构建查询参数
  let params = {
    pageNo: pageNo.value,
    pageSize: pageSize.value,
    name: name.value ? name.value : null,
    phone: phone.value ? phone.value : null,
    height: height.value ? height.value : null
  }
  let result = await coachPageService(params)
  //渲染视图
  total.value = result.data.total
  coaches.value = result.data.list
}

//刷新
coachList()


//添加私教的表单模型
let coachModel: Coach = reactive({
  id: 0,
  name: '',
  phone: '',
  height: '',
  weight: '',
  salary: ''
})

//创建token仓库
const tokenStore = useTokenStore()

/**
 *  添加私教
 */
const addCoach = async () => {
  //解构模型，抛弃id属性（因为添加的请求不需要）
  const{id, ...addModel} = coachModel
  await coachAddService(addModel)
  ElMessage.success("添加成功")
  //关闭抽屉
  visibleDrawer.value = false
  await coachList()
}

//展示添加私教时的抽屉
const showAddDrawer = () => {
  visibleDrawer.value = true
  direction.value = 'ltr'
  drawerTitle.value = '添加私教'
  clearCoachData()
}

//清空私教数据模型
const clearCoachData = () => {
  //id是不展示的，所以无所谓清除不清除
  coachModel.name = ''
  coachModel.phone = ''
  coachModel.height = ''
  coachModel.weight = ''
  coachModel.salary = ''
}

/**
 *  修改私教
 */
const updateEquipment = async () => {
  await coachUpdateService(coachModel)
  ElMessage.success("修改成功")
  //刷新
  await coachList()
  clearCoachData()
  //关闭抽屉
  visibleDrawer.value = false
}

//点击修改按钮后
const showEditDrawer = (row: Coach) => {
  visibleDrawer.value = true
  direction.value = 'rtl'
  drawerTitle.value = '修改私教'
  //回显数据
  coachModel = row
}

//监视抽屉的关闭：用户在抽屉界面如果什么都不做然后退出，此时数据不能变化
watch(visibleDrawer,(value: boolean) => {
  if (!value){   //当抽屉关闭时
    coachList()     //刷新
  }
})

/**
 *  删除私教
 * @param row
 */
const deleteArticle = (row: Coach) => {
  //给用户提示确认框
  ElMessageBox.confirm(
      '是否删除该私教',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    await coachDeleteService(row)
    ElMessage({
      type: 'success',
      message: '删除成功',
    })
    //重新获取分类列表
    await coachList()
  })
}



</script>

<template>

  <el-card class="page-container">

    <!--    卡片标题-->
    <template #header>
      <div class="header">
        <span>私教管理</span>
        <div class="extra">
          <el-button type="primary" @click="showAddDrawer">添加私教</el-button>
        </div>
      </div>
    </template>

    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="私教名称：">
        <el-input v-model="name" placeholder="请输入" style="width: 120px"></el-input>
      </el-form-item>

      <el-form-item label="私教电话：">
        <el-input v-model="phone" placeholder="请输入" style="width: 180px"></el-input>
      </el-form-item>

      <el-form-item label="私教身高：">
        <el-input v-model="height" placeholder="请输入" style="width: 120px"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="coachList">搜索</el-button>
        <el-button @click="name = '';phone = '';height = '';coachList()">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据列表 -->
    <el-table :data="coaches" style="width: 100%">
      <el-table-column label="ID" width="100" prop="id"></el-table-column>
      <el-table-column label="姓名" width="180" prop="name"></el-table-column>
      <el-table-column label="电话" width="150" prop="phone"> </el-table-column>
      <el-table-column label="身高" width="120" prop="height"></el-table-column>
      <el-table-column label="体重" width="180"  prop="weight"></el-table-column>
      <el-table-column label="加入时间" width="200" prop="joinDate"></el-table-column>
      <el-table-column label="薪水" width="200" prop="salary"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="EditPen" circle plain type="primary" @click="showEditDrawer(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteArticle(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="空数据" />
      </template>
    </el-table>
    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNo" v-model:page-size="pageSize" :page-sizes="[3, 5 ,10, 15]"
                   layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
                   @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />


    <!-- 抽屉->用于添加私教和修改私教  -->
    <el-drawer v-model="visibleDrawer" :title="drawerTitle" :direction="direction" size="25%">
      <!-- 表单 -->
      <el-form :model="coachModel" class="drawer-form">
        <el-form-item label="私教姓名：">
          <el-input v-model="coachModel.name" style="width: 150px"></el-input>
        </el-form-item>

        <el-form-item label="私教电话：">
          <el-input v-model="coachModel.phone" style="width: 150px"></el-input>
        </el-form-item>

        <el-form-item label="私教身高：">
          <el-input v-model="coachModel.height" style="width: 150px"></el-input>
        </el-form-item>

        <el-form-item label="私教体重：" >
          <el-input v-model="coachModel.weight" style="width: 150px"></el-input>
        </el-form-item>

        <el-form-item label="私教薪水：" >
          <el-input v-model="coachModel.salary" style="width: 150px"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary"
                     @click="drawerTitle === '添加私教' ? addCoach() : updateEquipment()">保存</el-button>
          <el-button type="info"
                     @click="clearCoachData">重置</el-button>
        </el-form-item>

      </el-form>

    </el-drawer>

  </el-card>

</template>

<style scoped lang="scss">

.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .drawer-form{
    display: flex;
    flex-direction: column;
    align-items: center;
  }

}

/* 抽屉样式 */
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}


</style>