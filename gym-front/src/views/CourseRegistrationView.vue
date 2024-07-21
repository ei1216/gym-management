<script setup lang="ts">
import {Delete, EditPen} from "@element-plus/icons-vue";
import {reactive, ref, watch} from "vue";
import {useTokenStore} from "@/stores/token";
import {ElMessage, ElMessageBox} from "element-plus";
import {courseListService} from "@/api/course";
import {
  courseRegistrationDeleteService,
  courseRegistrationPageService,
  courseRegistrationUpdateService
} from "@/api/courseRegistration";
import type {CourseRegistration} from "@/types/courseRegistration";
import type {Course} from "@/types/course";

//搜索条件-课程id
const courseId = ref('')
//搜索条件-如会员手机号，但需要在后端和数据库更改一下，暂时不写

//课程列表数据模型
const courses = ref<Course[]>([])

//课程报名列表数据模型
const courseRegs = ref([])


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



//获取课程列表
const courseList = async () => {
  let result = await courseListService()
  courses.value = result.data
}
//页面渲染后即调用
courseList()


//当每页条数发生了变化，调用此函数
const onSizeChange = (size: number) => {
  pageSize.value = size
  courseRegList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num: number) => {
  pageNo.value = num
  courseRegList()
}


/**
 * 获取课程报名列表数据
 */
const courseRegList = async () => {
  let params = {
    pageNo: pageNo.value,
    pageSize: pageSize.value,
    courseId: courseId.value ? courseId.value : null
  }
  let result = await courseRegistrationPageService(params)
  //渲染视图
  total.value = result.data.total
  courseRegs.value = result.data.list
}

//刷新
courseRegList()


//更改课程报名的表单模型
let courseRegModel: CourseRegistration = reactive({
  id: '',
  courseId: '',
  courseName: '',
  memberId: '',
  memberName: ''
})

//创建token仓库
const tokenStore = useTokenStore()


//清空课程报名数据模型
const clearCourseRegData = () => {
  courseRegModel.id = ''
  courseRegModel.courseId = ''
  courseRegModel.courseName = ''
  courseRegModel.memberName = ''
  courseRegModel.memberId = ''
}

/**
 *  修改课程报名
 */
const updateCourseReg = async () => {
  await courseRegistrationUpdateService(courseRegModel)
  ElMessage.success("修改成功")
  //刷新
  await courseRegList()
  clearCourseRegData()
  //关闭抽屉
  visibleDrawer.value = false
}

//点击编辑按钮后
const showEditDrawer = (row: CourseRegistration) => {
  visibleDrawer.value = true
  direction.value = 'rtl'
  drawerTitle.value = '修改课程报名'
  //回显数据
  courseRegModel = row
}

//监视抽屉的关闭：用户在抽屉界面如果什么都不做然后退出，此时数据不能变化
watch(visibleDrawer,(value: boolean) => {
  if (!value){   //当抽屉关闭时
    courseRegList()     //刷新
  }
})

/**
 *  删除课程报名
 * @param row
 */
const deleteCourseReg = (row: CourseRegistration) => {
  //给用户提示确认框
  ElMessageBox.confirm(
      '是否删除该课程报名',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    await courseRegistrationDeleteService(row)
    ElMessage({
      type: 'success',
      message: '删除成功',
    })
    //重新获取分类列表
    await courseRegList()
  })
}



</script>

<template>

  <el-card class="page-container">

    <!--    卡片标题-->
    <template #header>
      <div class="header">
        <span>课程报名管理</span>

      </div>
    </template>

    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="课程名称：">
        <el-select placeholder="请选择" v-model="courseId" style="width: 160px">
          <el-option v-for="c in courses" :key="c.id" :label="c.name" :value="c.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="courseRegList">搜索</el-button>
        <el-button @click="courseId = '';courseRegList()">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据列表 -->
    <el-table :data="courseRegs" style="width: 100%">
      <el-table-column label="ID" width="100" prop="id"></el-table-column>
      <el-table-column label="会员ID" width="180" prop="memberId"></el-table-column>
      <el-table-column label="会员名称" width="150" prop="memberName"> </el-table-column>
      <el-table-column label="课程ID" width="120" prop="courseId"></el-table-column>
      <el-table-column label="课程名称" width="180"  prop="courseName"></el-table-column>
      <el-table-column label="报名时间" width="300" prop="registrationDate"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="EditPen" circle plain type="primary" @click="showEditDrawer(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteCourseReg(row)"></el-button>
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