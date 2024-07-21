<script setup lang="ts">
import {Delete, EditPen} from "@element-plus/icons-vue";
import {reactive, ref, watch} from "vue";
import {
  equipmentAddService,
  equipmentDeleteService,
  equipmentPageService,
  equipmentUpdateService
} from "@/api/equipment";
import type {Equipment} from "@/types/equipment";
import {useTokenStore} from "@/stores/token";
import {ElMessage, ElMessageBox} from "element-plus";

//搜索条件-器材名称
const name = ref('')

//搜索条件-器材状态
const status = ref('')

//器材列表数据模型
const equipments = ref([])

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
  equipmentList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num: number) => {
  pageNo.value = num
  equipmentList()
}

/**
 * 获取器材列表数据
 */
const equipmentList = async () => {
  let params = {
    pageNo: pageNo.value,
    pageSize: pageSize.value,
    name: name.value ? name.value : null,   //有值的话就用这个值，否则为null
    status: status.value ? status.value : null
  }
  let result = await equipmentPageService(params)
  //渲染视图
  total.value = result.data.total
  equipments.value = result.data.list
}

//刷新
equipmentList()


//添加器材的表单模型
let equipmentModel: Equipment = reactive({
  id: 0,
  name: '',
  quantity: '',
  status: '',
  notes: '',
})

//创建token仓库
const tokenStore = useTokenStore()

/**
 *  添加器材
 */
const addEquipment = async () => {
  //解构模型，抛弃id属性（因为添加的请求不需要）
  const{id, ...addModel} = equipmentModel
  await equipmentAddService(addModel)
  ElMessage.success("添加成功")
  //关闭抽屉
  visibleDrawer.value = false
  await equipmentList()
}

//展示添加器材时的抽屉
const showAddDrawer = () => {
  visibleDrawer.value = true
  direction.value = 'ltr'
  drawerTitle.value = '添加器材'
  clearEquipmentData()
}

//清空器材数据模型
const clearEquipmentData = () => {
  equipmentModel.name = ''
  equipmentModel.quantity = ''
  equipmentModel.status = ''
  equipmentModel.notes = ''
}

/**
 *  修改器材
 */
const updateEquipment = async () => {
  await equipmentUpdateService(equipmentModel)
  ElMessage.success("修改成功")
  //刷新
  await equipmentList()
  clearEquipmentData()
  //关闭抽屉
  visibleDrawer.value = false
}

//点击编辑按钮后
const showEditDrawer = (row: Equipment) => {
  visibleDrawer.value = true
  direction.value = 'rtl'
  drawerTitle.value = '修改器材'
  //回显数据
  equipmentModel = row
}

//监视抽屉的关闭：用户在抽屉界面如果什么都不做然后退出，此时数据不能变化
watch(visibleDrawer,(value: boolean) => {
  if (!value){   //当抽屉关闭时
    equipmentList()     //刷新
  }
})

/**
 *  删除器材
 * @param row
 */
const deleteEquipment = (row: Equipment) => {
  //给用户提示确认框
  ElMessageBox.confirm(
      '是否删除该器材',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    await equipmentDeleteService(row)
    ElMessage({
      type: 'success',
      message: '删除成功',
    })
    //重新获取分类列表
    await equipmentList()
  })
}



</script>

<template>

  <el-card class="page-container">

<!--    卡片标题-->
    <template #header>
      <div class="header">
        <span>器材管理</span>
        <div class="extra">
          <el-button type="primary" @click="showAddDrawer">添加器材</el-button>
        </div>
      </div>
    </template>

    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="器材名称：">
        <el-input v-model="name" placeholder="请输入" style="width: 120px"></el-input>
      </el-form-item>

      <el-form-item label="器材状态：">
        <el-select placeholder="请选择" style="width: 120px" v-model="status">
          <el-option label="正常" value="正常"></el-option>
          <el-option label="维护" value="维护"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="equipmentList">搜索</el-button>
        <el-button @click="name = '';status = '';equipmentList()">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据列表 -->
    <el-table :data="equipments" style="width: 100%">
      <el-table-column label="ID" width="100" prop="id"></el-table-column>
      <el-table-column label="器材名称" width="180" prop="name"></el-table-column>
      <el-table-column label="数量" width="150" prop="quantity"> </el-table-column>
      <el-table-column label="状态" width="120" prop="status"></el-table-column>
      <el-table-column label="更新时间" width="180"  prop="updateDate"></el-table-column>
      <el-table-column label="备注" width="400" prop="notes"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="EditPen" circle plain type="primary" @click="showEditDrawer(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteEquipment(row)"></el-button>
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


    <!-- 抽屉->用于添加器材和修改器材  -->
    <el-drawer v-model="visibleDrawer" :title="drawerTitle" :direction="direction" size="30%">
      <!-- 表单 -->
      <el-form :model="equipmentModel" label-width="100px" class="drawer-form">
        <el-form-item label="器材名称：" >
          <el-input v-model="equipmentModel.name" style="width: 180px"></el-input>
        </el-form-item>

        <el-form-item label="器材数量：" >
          <el-input v-model="equipmentModel.quantity" style="width: 180px"></el-input>
        </el-form-item>

        <el-form-item label="器材状态：">
          <el-select placeholder="请选择" v-model="equipmentModel.status" style="width: 180px">
            <el-option label="正常" value="正常"></el-option>
            <el-option label="维护" value="维护"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="备注：" >
          <el-input v-model="equipmentModel.notes" type="textarea" :rows="3" style="width: 180px"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary"
                     @click="drawerTitle === '添加器材' ? addEquipment() : updateEquipment()">保存</el-button>
          <el-button type="info"
                     @click="clearEquipmentData">重置</el-button>
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