<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="用户编号" prop="userId">
              <el-input v-model="queryParams.userId" placeholder="请输入用户编号" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="起减价" prop="startMoney">
              <el-input v-model="queryParams.startMoney" placeholder="请输入起减价" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="减少价格" prop="discountMoney">
              <el-input v-model="queryParams.discountMoney" placeholder="请输入减少价格" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="创建时间" prop="createTime">
              <el-date-picker clearable
                v-model="queryParams.createTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择创建时间"
              />
            </el-form-item>
            <el-form-item label="结束时间" prop="endTime">
              <el-date-picker clearable
                v-model="queryParams.endTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择结束时间"
              />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['financinglink:coupon:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['financinglink:coupon:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['financinglink:coupon:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['financinglink:coupon:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="couponList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="用户编号" align="center" prop="userId" />
        <el-table-column label="起减价" align="center" prop="startMoney" />
        <el-table-column label="减少价格" align="center" prop="discountMoney" />
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结束时间" align="center" prop="endTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['financinglink:coupon:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['financinglink:coupon:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改优惠卷对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="couponFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="起减价" prop="startMoney">
          <el-input v-model="form.startMoney" placeholder="请输入起减价" />
        </el-form-item>
        <el-form-item label="减少价格" prop="discountMoney">
          <el-input v-model="form.discountMoney" placeholder="请输入减少价格" />
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

<script setup name="Coupon" lang="ts">
import { listCoupon, getCoupon, delCoupon, addCoupon, updateCoupon } from '@/api/financinglink/coupon';
import { CouponVO, CouponQuery, CouponForm } from '@/api/financinglink/coupon/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const couponList = ref<CouponVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const couponFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: CouponForm = {
  startMoney: undefined,
  discountMoney: undefined,
}
const data = reactive<PageData<CouponForm, CouponQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userId: undefined,
    startMoney: undefined,
    discountMoney: undefined,
    createTime: undefined,
    endTime: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询优惠卷列表 */
const getList = async () => {
  loading.value = true;
  const res = await listCoupon(queryParams.value);
  couponList.value = res.rows;
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
  couponFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: CouponVO[]) => {
  ids.value = selection.map(item => item.userId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加优惠卷";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: CouponVO) => {
  reset();
  const _userId = row?.userId || ids.value[0]
  const res = await getCoupon(_userId);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改优惠卷";
}

/** 提交按钮 */
const submitForm = () => {
  couponFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.userId) {
        await updateCoupon(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addCoupon(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: CouponVO) => {
  const _userIds = row?.userId || ids.value;
  await proxy?.$modal.confirm('是否确认删除优惠卷编号为"' + _userIds + '"的数据项？').finally(() => loading.value = false);
  await delCoupon(_userIds);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('financinglink/coupon/export', {
    ...queryParams.value
  }, `coupon_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
