<template>
  <div style="width: 1400px;" class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter"
      :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item class="el_form" label="地址" prop="address">
              <el-input v-model="queryParams.address" placeholder="请输入地址" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item class="el_form" label="种植作物" prop="crop">
              <el-input v-model="queryParams.crop" placeholder="请输入种植作物" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item class="el_form" label="电话" prop="phone">
              <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item class="el_form" label="姓名" prop="name">
              <el-input v-model="queryParams.name" placeholder="请输入姓名" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
              <el-button icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </transition>

    <el-card shadow="never">
      <template #header>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport"
              v-hasPermi="['financinglink:appointment:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="appointmentList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="编号" align="center" prop="id" v-if="false" />
        <el-table-column label="地址" align="center" prop="address" />
        <el-table-column label="种植作物" align="center" prop="crop" />
        <el-table-column label="种植作物详细信息" align="center" prop="cropDetail" />
        <el-table-column label="作物条件" align="center" prop="cropCondition" />
        <el-table-column label="土壤条件" align="center" prop="edaphicCondition" />
        <el-table-column label="面积" align="center" prop="area" />
        <el-table-column label="电话" align="center" prop="phone" />
        <el-table-column label="姓名" align="center" prop="name" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-button link type="primary" @click="handleUpdate(scope.row)"
              v-hasPermi="['financinglink:appointment:edit']">详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改预约对话框 -->
    <el-dialog title="详情" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="appointmentFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="地址" prop="address">
          <el-input disabled v-model="form.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="种植作物" prop="crop">
          <el-input disabled v-model="form.crop" placeholder="请输入种植作物" />
        </el-form-item>
        <el-form-item label="作物详细" prop="cropDetail">
          <el-input disabled v-model="form.cropDetail" placeholder="请输入种植作物详细信息" />
        </el-form-item>
        <el-form-item label="作物条件" prop="cropCondition">
          <el-input disabled v-model="form.cropCondition" placeholder="请输入作物条件" />
        </el-form-item>
        <el-form-item label="土壤条件" prop="edaphicCondition">
          <el-input disabled v-model="form.edaphicCondition" placeholder="请输入土壤条件" />
        </el-form-item>
        <el-form-item label="面积" prop="area">
          <el-input disabled v-model="form.area" placeholder="请输入面积" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input disabled v-model="form.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input disabled v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Appointment" lang="ts">
import { listAppointment, getAppointment, delAppointment, addAppointment, updateAppointment } from '@/api/financinglink/appointment';
import { AppointmentVO, AppointmentQuery, AppointmentForm } from '@/api/financinglink/appointment/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const appointmentList = ref<AppointmentVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const appointmentFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: AppointmentForm = {
  address: undefined,
  crop: undefined,
  cropDetail: undefined,
  cropCondition: undefined,
  edaphicCondition: undefined,
  area: undefined,
  phone: undefined,
  name: undefined,
}
const data = reactive<PageData<AppointmentForm, AppointmentQuery>>({
  form: { ...initFormData },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    address: undefined,
    crop: undefined,
    phone: undefined,
    name: undefined,
    createBy: 1,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询预约列表 */
const getList = async () => {
  loading.value = true;
  const res = await listAppointment(queryParams.value);
  appointmentList.value = res.rows;
  total.value = res.total;
  loading.value = false;
}

/** 取消按钮 */
const cancel = () => {
  reset();
  dialog.visible = false;
}

/** 表单重置 */
const reset = () => {
  form.value = { ...initFormData };
  appointmentFormRef.value?.resetFields();
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
}

/** 多选框选中数据 */
const handleSelectionChange = (selection: AppointmentVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加预约";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: AppointmentVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getAppointment(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改预约";
}

/** 提交按钮 */
const submitForm = () => {
}

/** 删除按钮操作 */
const handleDelete = async (row?: AppointmentVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除预约编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delAppointment(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('financinglink/appointment/export', {
    ...queryParams.value
  }, `appointment_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>

<style scoped>
.el_form {
  width: 400px;
}
</style>
