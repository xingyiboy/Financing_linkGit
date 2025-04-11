<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="编号" prop="id">
              <el-input v-model="queryParams.id" placeholder="请输入编号" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="用户编号" prop="userId">
              <el-input v-model="queryParams.userId" placeholder="请输入用户编号" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="地址编码" prop="addressCode">
              <el-input v-model="queryParams.addressCode" placeholder="请输入地址编码" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="详细地址" prop="addressDetail">
              <el-input v-model="queryParams.addressDetail" placeholder="请输入详细地址" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="是否是默认地址 0不是 1是" prop="isDefault">
              <el-input v-model="queryParams.isDefault" placeholder="请输入是否是默认地址 0不是 1是" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="收货人姓名" prop="name">
              <el-input v-model="queryParams.name" placeholder="请输入收货人姓名" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="收货人手机号" prop="phone">
              <el-input v-model="queryParams.phone" placeholder="请输入收货人手机号" clearable @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['financinglink:address:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['financinglink:address:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['financinglink:address:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['financinglink:address:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="addressList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="编号" align="center" prop="id" v-if="false" />
        <el-table-column label="用户编号" align="center" prop="userId" />
        <el-table-column label="地址编码" align="center" prop="addressCode" />
        <el-table-column label="详细地址" align="center" prop="addressDetail" />
        <el-table-column label="是否是默认地址 0不是 1是" align="center" prop="isDefault">
          <template #default="scope">
            <dict-tag :options="adress_default" :value="scope.row.isDefault"/>
          </template>
        </el-table-column>
        <el-table-column label="收货人姓名" align="center" prop="name" />
        <el-table-column label="收货人手机号" align="center" prop="phone" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['financinglink:address:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['financinglink:address:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改用户地址对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="addressFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户编号" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户编号" />
        </el-form-item>
        <el-form-item label="地址编码" prop="addressCode">
          <el-input v-model="form.addressCode" placeholder="请输入地址编码" />
        </el-form-item>
        <el-form-item label="详细地址" prop="addressDetail">
          <el-input v-model="form.addressDetail" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="是否是默认地址 0不是 1是" prop="isDefault">
          <el-input v-model="form.isDefault" placeholder="请输入是否是默认地址 0不是 1是" />
        </el-form-item>
        <el-form-item label="收货人姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入收货人姓名" />
        </el-form-item>
        <el-form-item label="收货人手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入收货人手机号" />
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

<script setup name="Address" lang="ts">
import { listAddress, getAddress, delAddress, addAddress, updateAddress } from '@/api/financinglink/address';
import { AddressVO, AddressQuery, AddressForm } from '@/api/financinglink/address/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const addressList = ref<AddressVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const addressFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: AddressForm = {
  userId: undefined,
  addressCode: undefined,
  addressDetail: undefined,
  isDefault: undefined,
  name: undefined,
  phone: undefined
}
const data = reactive<PageData<AddressForm, AddressQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: undefined,
    userId: undefined,
    addressCode: undefined,
    addressDetail: undefined,
    isDefault: undefined,
    name: undefined,
    phone: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询用户地址列表 */
const getList = async () => {
  loading.value = true;
  const res = await listAddress(queryParams.value);
  addressList.value = res.rows;
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
  form.value = {...initFormData};
  addressFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: AddressVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加用户地址";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: AddressVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getAddress(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改用户地址";
}

/** 提交按钮 */
const submitForm = () => {
  addressFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateAddress(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addAddress(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: AddressVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除用户地址编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delAddress(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('financinglink/address/export', {
    ...queryParams.value
  }, `address_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
