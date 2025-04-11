<template>
  <div style="margin-bottom: 100px;"></div>
  <div style="margin-top: 20px; width: 200px; margin-left: 320px; position: absolute; left: 0;">我的收货地址</div>
  <el-button @click="handleAdd" type="success"
    style="margin-top:60px; margin-left: 320px; position: absolute; left: 0;">添加</el-button>
  <div
    style="width: 1100px; border: 1px solid #ccc; padding: 20px; margin-bottom: 15px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);"
    v-for="item in addressList" :key="item.id">
    <div style="display: flex; justify-content: space-between; margin-top: 4px; margin-bottom: 4px;">
      <!-- 左侧 -->
      <div style="flex: 1; padding-right: 20px;">
        <div>收货人: {{ item.name }}</div>
        <div>收货地址: {{ item.addressCode }}</div>
        <div>详细地址: {{ item.addressDetail }}</div>
        <div>收货手机号: {{ item.phone }}</div>
      </div>

      <!-- 右侧 -->
      <div style="flex-shrink: 0; text-align: right;">
        <div v-if="item.isDefault == 1" style="color: #4CAF50; margin-top: -10px;margin-bottom: -10px;">默认收货地址</div>
        <div style="margin-top: 10px;">
          <button
            style="background-color: #f0ad4e;margin-top: 20px;  color: white; border: none; padding: 5px 10px; font-size: 14px; border-radius: 4px; cursor: pointer; margin-right: 10px;"
            @click="setDefault(item)">
            设置默认
          </button>
          <button
            style="background-color: #5bc0de; color: white; border: none; padding: 5px 10px; font-size: 14px; border-radius: 4px; cursor: pointer; margin-right: 10px;"
            @click="handleUpdate(item)">
            编辑
          </button>
          <button
            style="background-color: #d9534f; color: white; border: none; padding: 5px 10px; font-size: 14px; border-radius: 4px; cursor: pointer;"
            @click="handleDelete(item)">
            删除
          </button>
        </div>
      </div>
    </div>
  </div>



  <!-- 添加或修改用户地址对话框 -->
  <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
    <el-form ref="addressFormRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="" prop="addressCode">
        <span style="font-weight: bold; margin-left: -40px; margin-right: 10px">地址</span>

        <el-cascader size="large" :options="pcaTextArr" :props="props1" clearable v-model="form.addressCode"
          placeholder="请选择地址" />
      </el-form-item>
      <el-form-item label="详细地址" prop="addressDetail">
        <el-input v-model="form.addressDetail" placeholder="请输入详细地址" />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入姓名" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入手机号" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>

</template>

<script setup name="Address" lang="ts">
import { listAddress, getAddress, delAddress, addAddress, updateAddress } from '@/api/financinglink/address';
import { AddressVO, AddressQuery, AddressForm } from '@/api/financinglink/address/types';
import { pcaTextArr } from 'element-china-area-data';

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
  form: { ...initFormData },
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
  form.value = { ...initFormData };
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

  //取出地址码
  if (res.data.addressCode && res.data.addressCode != '') {
    res.data.addressCode = res.data.addressCode.split(',');
  }
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改用户地址";
}

/** 提交按钮 */
const submitForm = () => {
  addressFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      form.value.addressCode = form.value.addressCode.join(',');
      if (form.value.isDefault != 1) {
        form.value.isDefault = 0
      }
      if (form.value.id) {
        await updateAddress(form.value).finally(() => buttonLoading.value = false);
      } else {
        await addAddress(form.value).finally(() => buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/**
 * 设置为默认地址
 */
const setDefault = async (item) => {
  const updateItem = { ...item }
  updateItem.isDefault = 1
  await updateAddress(updateItem).finally(() => buttonLoading.value = false);
  proxy?.$modal.msgSuccess("操作成功");
  dialog.visible = false;
  await getList();
}

/** 删除按钮操作 */
const handleDelete = async (row?: AddressVO) => {
  if (row.isDefault == 1) {
    proxy.$modal.msgError("默认地址不能删除");
    return;
  }
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

<style lang="scss" scoped></style>
