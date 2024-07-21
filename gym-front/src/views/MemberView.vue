<script setup lang="ts">
import {Delete, EditPen, Operation} from "@element-plus/icons-vue";
import {reactive, ref, watch} from "vue";
import {useTokenStore} from "@/stores/token";
import {ElMessage, ElMessageBox} from "element-plus";
import {memberAddService, memberDeleteService, memberPageService, memberUpdateService} from "@/api/member";
import type {Member} from "@/types/member";
import {courseListService} from "@/api/course";
import type {Course} from "@/types/course";
import {courseRegistrationAddService} from "@/api/courseRegistration";

//搜索条件-会员姓名
const name = ref('')
//搜索条件-会员电话
const phone = ref('')
//搜索条件-会员类型
const type = ref('')

//会员列表数据模型
const members = ref([])

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

//是否显示对话框
const dialogVisible = ref(false)
//课程报名数据模型
let courseRegistrationModel = reactive({
  memberId: '',
  memberName: '',   //这个属性其实是请求参数之外的参数，但是不影响发送请求（可能就是后端会自动屏蔽无效的参数）
  courseId: ''
})

//课程列表数据模型
const courses = ref<Course[]>([])


//当每页条数发生了变化，调用此函数
const onSizeChange = (size: number) => {
  pageSize.value = size
  memberList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num: number) => {
  pageNo.value = num
  memberList()
}

/**
 * 获取会员列表数据
 */
const memberList = async () => {
  //构建查询参数
  let params = {
    pageNo: pageNo.value,
    pageSize: pageSize.value,
    name: name.value ? name.value : null,
    phone: phone.value ? phone.value : null,
    type: type.value ? type.value : null
  }
  let result = await memberPageService(params)
  //渲染视图
  total.value = result.data.total
  members.value = result.data.list
}

//刷新
memberList()


//添加会员的表单模型
let memberModel: Member = reactive({
  id: '',
  name: '',
  phone: '',
  type: ''
})

//创建token仓库
const tokenStore = useTokenStore()

/**
 *  添加会员
 */
const addMember = async () => {
  //解构模型，抛弃id属性（因为添加的请求不需要）
  const{id, ...addModel} = memberModel
  await memberAddService(addModel)
  ElMessage.success("添加成功")
  //关闭抽屉
  visibleDrawer.value = false
  await memberList()
}

//展示添加会员时的抽屉
const showAddDrawer = () => {
  visibleDrawer.value = true
  direction.value = 'ltr'
  drawerTitle.value = '添加会员'
  clearMemberData()
}

//清空会员数据模型
const clearMemberData = () => {
  memberModel.name = ''
  memberModel.phone = ''
  memberModel.type = ''
}

//清空课程报名数据
const clearCourseRegistrationData = () => {
  courseRegistrationModel.memberName = ''
  courseRegistrationModel.courseId = ''
  courseRegistrationModel.memberId = ''
}

/**
 *  修改会员
 */
const updateMember = async () => {
  await memberUpdateService(memberModel)
  ElMessage.success("修改成功")
  //刷新
  await memberList()
  clearMemberData()
  //关闭抽屉
  visibleDrawer.value = false
}

//点击修改按钮后
const showEditDrawer = (row: Member) => {
  visibleDrawer.value = true
  direction.value = 'rtl'
  drawerTitle.value = '修改会员'
  //回显数据
  memberModel = row
}

//监视抽屉的关闭：用户在抽屉界面如果什么都不做然后退出，此时数据不能变化
watch(visibleDrawer,(value: boolean) => {
  if (!value){   //当抽屉关闭时
    memberList()     //刷新
  }
})

/**
 *  删除会员
 * @param row
 */
const deleteMember = (row: Member) => {
  //给用户提示确认框
  ElMessageBox.confirm(
      '是否删除该会员',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    await memberDeleteService(row)
    ElMessage({
      type: 'success',
      message: '删除成功',
    })
    //重新获取分类列表
    await memberList()
  })
}

//展示课程报名对话框
const showDialog = (row: Member) =>{
  dialogVisible.value = true

  courseRegistrationModel.memberId = <string>row.id
  courseRegistrationModel.memberName= row.name
}

//获取课程列表
const courseList = async () => {
  let result = await courseListService()
  courses.value = result.data
}
//页面渲染后即调用
courseList()

//监视对话框的关闭：用户在对话框中如果什么都不做然后退出，此时数据不能变化
watch(dialogVisible,(value: boolean) => {
  if (!value){   //当关闭时
    memberList()     //刷新
    clearCourseRegistrationData()
  }
})

//报名课程
const registerCourse = async ()=> {
  await courseRegistrationAddService(courseRegistrationModel)
  //清除模型的数据
  clearCourseRegistrationData()
  ElMessage.success("报名成功")
  dialogVisible.value = false
}



</script>

<template>

  <el-card class="page-container">

    <!--    卡片标题-->
    <template #header>
      <div class="header">
        <span>会员管理</span>
        <div class="extra">
          <el-button type="primary" @click="showAddDrawer">添加会员</el-button>
        </div>
      </div>
    </template>

    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="会员姓名：">
        <el-input v-model="name" placeholder="请输入" style="width: 120px"></el-input>
      </el-form-item>

      <el-form-item label="会员电话：">
        <el-input v-model="phone" placeholder="请输入" style="width: 180px"></el-input>
      </el-form-item>

      <el-form-item label="会员类型：">
        <el-select placeholder="请选择" style="width: 120px" v-model="type">
          <el-option label="月卡会员" value="月卡会员"></el-option>
          <el-option label="季卡会员" value="季卡会员"></el-option>
          <el-option label="年卡会员" value="年卡会员"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="memberList">搜索</el-button>
        <el-button @click="name = '';phone = '';type = '';memberList()">重置</el-button>
      </el-form-item>

    </el-form>


    <el-dialog v-model="dialogVisible" title="会员报名课程"  width="350">

      <el-form :model="courseRegistrationModel" label-width="100px" class="dialog-form">
        <el-form-item label="会员ID：">
          <el-input v-model="courseRegistrationModel.memberId" disabled style="width: 160px"></el-input>
        </el-form-item>

        <el-form-item label="会员姓名：">
          <el-input v-model="courseRegistrationModel.memberName" disabled style="width: 160px"></el-input>
        </el-form-item>

        <el-form-item label="课程名称：">
          <el-select placeholder="请选择" v-model="courseRegistrationModel.courseId" style="width: 160px">
            <el-option v-for="c in courses" :key="c.id" :label="c.name" :value="c.id">
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="registerCourse">确定</el-button>
        </div>
      </template>
    </el-dialog>


    <!-- 数据列表 -->
    <el-table :data="members" style="width: 100%">
      <el-table-column label="ID" width="100" prop="id"></el-table-column>
      <el-table-column label="姓名" width="180" prop="name"></el-table-column>
      <el-table-column label="电话" width="150" prop="phone"> </el-table-column>
      <el-table-column label="会员类型" width="120" prop="type"></el-table-column>
      <el-table-column label="加入时间" width="200" prop="joinDate"></el-table-column>
      <el-table-column label="过期时间" width="200" prop="expiryDate"></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button :icon="EditPen" circle plain type="primary" @click="showEditDrawer(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteMember(row)"></el-button>
        </template>
      </el-table-column>
      <el-table-column label="课程报名" width="100">
        <template #default="{ row }">
          <el-button :icon="Operation" circle plain type="primary" @click="showDialog(row)"></el-button>
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


    <!-- 抽屉->用于添加会员和修改会员  -->
    <el-drawer v-model="visibleDrawer" :title="drawerTitle" :direction="direction" size="25%">
      <!-- 表单 -->
      <el-form :model="memberModel" class="drawer-form">
        <el-form-item label="会员姓名：">
          <el-input v-model="memberModel.name" style="width: 150px"></el-input>
        </el-form-item>

        <el-form-item label="会员电话：">
          <el-input v-model="memberModel.phone" style="width: 150px"></el-input>
        </el-form-item>

        <el-form-item label="会员类型：">
          <el-select placeholder="请选择" style="width: 150px" v-model="memberModel.type">
            <el-option label="月卡会员" value="月卡会员"></el-option>
            <el-option label="季卡会员" value="季卡会员"></el-option>
            <el-option label="年卡会员" value="年卡会员"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary"
                     @click="drawerTitle === '添加会员' ? addMember() : updateMember()">保存</el-button>
          <el-button type="info"
                     @click="clearMemberData">重置</el-button>
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

  .dialog-form{
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