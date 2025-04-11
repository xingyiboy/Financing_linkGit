<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter"
      :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="专家姓名" prop="name">
              <el-input v-model="queryParams.name" placeholder="请输入专家姓名" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="职称" prop="professional">
              <el-input v-model="queryParams.professional" placeholder="请输入职称" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="专业" prop="major">
              <el-input v-model="queryParams.major" placeholder="请输入专业" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="电话" prop="phone">
              <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="单位" prop="address">
              <el-input v-model="queryParams.address" placeholder="请输入单位" clearable @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd"
              v-hasPermi="['financinglink:specialist:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()"
              v-hasPermi="['financinglink:specialist:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()"
              v-hasPermi="['financinglink:specialist:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport"
              v-hasPermi="['financinglink:specialist:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="specialistList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="编号" align="center" prop="id" v-if="true" />
        <el-table-column label="用户编号" align="center" prop="userId" />
        <el-table-column label="专家姓名" align="center" prop="name" />
        <el-table-column label="职称" align="center" prop="professional" />
        <el-table-column label="专业" align="center" prop="major" />
        <el-table-column label="电话" align="center" prop="phone" />
        <el-table-column label="单位" align="center" prop="address" />
        <el-table-column label="头像" align="center" prop="avatarUrl" width="100">
          <template #default="scope">
            <image-preview :src="scope.row.avatarUrl" :width="50" :height="50" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['financinglink:specialist:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                v-hasPermi="['financinglink:specialist:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改专家详情对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="specialistFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="专家姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入专家姓名" />
        </el-form-item>
        <el-form-item label="职称" prop="professional">
          <el-input v-model="form.professional" placeholder="请输入职称" />
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="form.major" placeholder="请输入专业" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="单位" prop="address">
          <el-input v-model="form.address" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <image-upload v-model="form.avatar" limit="1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Specialist" lang="ts">
import { isSpecialist, listSpecialist, getSpecialist, delSpecialist, addSpecialist, updateSpecialist } from '@/api/financinglink/specialist';
import { SpecialistVO, SpecialistQuery, SpecialistForm } from '@/api/financinglink/specialist/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const specialistList = ref<SpecialistVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const specialistFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: SpecialistForm = {
  name: undefined,
  professional: undefined,
  major: undefined,
  phone: undefined,
  address: undefined,
  avatar: undefined
}
const data = reactive<PageData<SpecialistForm, SpecialistQuery>>({
  form: { ...initFormData },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    professional: undefined,
    major: undefined,
    phone: undefined,
    address: undefined,
    avatar: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询专家详情列表 */
const getList = async () => {
  loading.value = true;
  const res = await listSpecialist(queryParams.value);
  specialistList.value = res.rows;
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
  specialistFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: SpecialistVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加专家详情";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: SpecialistVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getSpecialist(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改专家详情";
}

/** 提交按钮 */
const submitForm = () => {
  specialistFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateSpecialist(form.value).finally(() => buttonLoading.value = false);
      } else {
        await addSpecialist(form.value).finally(() => buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: SpecialistVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除专家详情编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delSpecialist(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('financinglink/specialist/export', {
    ...queryParams.value
  }, `specialist_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
