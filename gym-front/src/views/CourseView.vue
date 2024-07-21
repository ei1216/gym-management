<script setup lang="ts">
import {Delete, EditPen} from "@element-plus/icons-vue";
import {reactive, ref, watch} from "vue";
import {useTokenStore} from "@/stores/token";
import {ElMessage, ElMessageBox} from "element-plus";
import {courseAddService, courseDeleteService, coursePageService, courseUpdateService} from "@/api/course";
import type {Course, CourseWithCoachName} from "@/types/course";
import {coachListService} from "@/api/coach";
import type {Coach} from "@/types/coach";

//搜索条件-课程名称
const name = ref('')

//课程列表数据模型
const courses = ref<Course[]>([])
//带有私教名字的课程列表数据模型
const coursesWithCoachName = ref<CourseWithCoachName[]>([])
//私教列表数据模型
const coaches = ref<Coach[]>([])


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

//定义一周的星期数组
const weeks = [
  {
    value: '星期一',
    label: '星期一',
  },
  {
    value: '星期二',
    label: '星期二',
  },
  {
    value: '星期三',
    label: '星期三',
  },
  {
    value: '星期四',
    label: '星期四',
  },
  {
    value: '星期五',
    label: '星期五',
  },
  {
    value: '星期六',
    label: '星期六',
  },
  {
    value: '星期日',
    label: '星期日',
  },
]


//当每页条数发生了变化，调用此函数
const onSizeChange = (size: number) => {
  pageSize.value = size
  courseList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num: number) => {
  pageNo.value = num
  courseList()
}

//获取私教列表
const courseCoachList = async () => {
  let result = await coachListService()
  coaches.value = result.data
}
//页面渲染后即调用
courseCoachList()



/**
 * 获取课程列表数据
 */
const courseList = async () => {
  //构建查询参数
  let params = {
    pageNo: pageNo.value,
    pageSize: pageSize.value,
    name: name.value ? name.value : null,
  }
  let result = await coursePageService(params)
  //渲染视图
  total.value = result.data.total
  courses.value = result.data.list

  //下面的代码是处理数据：给数据模型扩展一个属性coachName，便于展示私教名

  // 创建一个从教练ID到教练名称的映射表
  const coachNameMap = new Map<string, string>();
  coaches.value.forEach(coach => {
    coachNameMap.set(<string>coach.id, coach.name);
  });

  // 使用 map() 方法返回一个新数组，数组中的元素为原始数组元素调用函数处理后的值
  coursesWithCoachName.value = courses.value.map(course => {
    // 根据课程的 coachId 查找教练名称
    const coachName = coachNameMap.get(course.coachId);

    // 返回扩展后的课程对象
    return {
      ...course, // 展开原始课程对象
      coachName // 添加 coachName 属性
    };
  });

}

//刷新
courseList()

//添加课程的表单模型
let courseModel: Course = reactive({
  id: '',
  name: '',
  description: '',
  coachId: '',
  startDate: '',
  endDate: '',
  daysOfWeek: '',
  price: ''
})

//创建token仓库
const tokenStore = useTokenStore()

/**
 *  添加课程
 */
const addCourse = async () => {
  //解构模型，抛弃id属性（因为添加的请求不需要）
  const{id, ...addModel} = courseModel
  await courseAddService(addModel)
  ElMessage.success("添加成功")
  //关闭抽屉
  visibleDrawer.value = false
  await courseList()
}

//展示添加课程时的抽屉
const showAddDrawer = () => {
  visibleDrawer.value = true
  direction.value = 'ltr'
  drawerTitle.value = '添加课程'
  clearCourseData()
}

//清空课程数据模型
const clearCourseData = () => {
  //id是不展示的，所以无所谓清除不清除
  courseModel.name = ''
  courseModel.description = ''
  courseModel.coachId = ''
  courseModel.startDate = ''
  courseModel.endDate = ''
  courseModel.daysOfWeek = ''
  courseModel.price = ''
}

/**
 *  修改课程
 */
const updateCourse = async () => {
  await courseUpdateService(courseModel)
  ElMessage.success("修改成功")
  //刷新
  await courseList()
  clearCourseData()
  //关闭抽屉
  visibleDrawer.value = false
}

//点击修改按钮后
const showEditDrawer = (row: Course) => {
  visibleDrawer.value = true
  direction.value = 'rtl'
  drawerTitle.value = '修改课程'
  //回显数据
  courseModel = row
}

//监视抽屉的关闭：用户在抽屉界面如果什么都不做然后退出，此时数据不能变化
watch(visibleDrawer,(value: boolean) => {
  if (!value){   //当抽屉关闭时
    courseList()     //刷新
    //clearCourseData()
  }
})

/**
 *  删除课程
 * @param row
 */
const deleteCourse = (row: Course) => {
  //给用户提示确认框
  ElMessageBox.confirm(
      '是否删除该课程',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    await courseDeleteService(row)
    ElMessage({
      type: 'success',
      message: '删除成功',
    })
    //重新获取分类列表
    await courseList()
  })
}



</script>

<template>

  <el-card class="page-container">

    <!--    卡片标题-->
    <template #header>
      <div class="header">
        <span>课程管理</span>
        <div class="extra">
          <el-button type="primary" @click="showAddDrawer">添加课程</el-button>
        </div>
      </div>
    </template>

    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="课程名称：">
        <el-input v-model="name" placeholder="请输入" style="width: 120px"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="courseList">搜索</el-button>
        <el-button @click="name = '';courseList()">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据列表 -->
    <el-table :data="coursesWithCoachName" style="width: 100%">
      <el-table-column label="ID" width="50" prop="id"></el-table-column>
      <el-table-column label="名称" width="180" prop="name"></el-table-column>
      <el-table-column label="描述" width="300" prop="description"> </el-table-column>
      <el-table-column label="私教" width="80" prop="coachName"></el-table-column>
      <el-table-column label="开始时间" width="150"  prop="startDate"></el-table-column>
      <el-table-column label="结束时间" width="150" prop="endDate"></el-table-column>
      <el-table-column label="每周上课日" width="120" prop="daysOfWeek"></el-table-column>
      <el-table-column label="价格" width="100" prop="price"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="EditPen" circle plain type="primary" @click="showEditDrawer(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteCourse(row)"></el-button>
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


    <!-- 抽屉->用于添加课程和修改课程  -->
    <el-drawer v-model="visibleDrawer" :title="drawerTitle" :direction="direction" size="25%">
      <!-- 表单 -->
      <el-form :model="courseModel" class="drawer-form">

        <el-form-item label="课程名称：">
          <el-input v-model="courseModel.name" style="width: 150px"></el-input>
        </el-form-item>

        <el-form-item label="课程描述：">
          <el-input
              v-model="courseModel.description"
              style="width: 150px"
              :rows="5"
              type="textarea"
              resize="none"
          />
        </el-form-item>

        <el-form-item label="课程私教：" >
          <el-select placeholder="请选择" v-model="courseModel.coachId" style="width: 150px">
            <el-option v-for="c in coaches" :key="c.id" :label="c.name" :value="c.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="开始时间：">
          <el-date-picker
              v-model="courseModel.startDate"
              type="date"
              style="width: 150px"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>

        <el-form-item label="结束时间：">
          <el-date-picker
              v-model="courseModel.endDate"
              type="date"
              style="width: 150px"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>

        <el-form-item label="每周上课：" >
          <el-select placeholder="请选择" v-model="courseModel.daysOfWeek" style="width: 150px">
            <el-option v-for="w in weeks" :label="w.label" :value="w.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="课程价格：" >
          <el-input v-model="courseModel.price" style="width: 150px"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary"
                     @click="drawerTitle === '添加课程' ? addCourse() : updateCourse()">保存</el-button>
          <el-button type="info"
                     @click="clearCourseData">重置</el-button>
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