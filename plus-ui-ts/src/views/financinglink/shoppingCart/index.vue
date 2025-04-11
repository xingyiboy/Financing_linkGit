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
            <el-form-item label="规格库存编号" prop="specificationId">
              <el-input v-model="queryParams.specificationId" placeholder="请输入规格库存编号" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="购买数量" prop="number">
              <el-input v-model="queryParams.number" placeholder="请输入购买数量" clearable @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['financinglink:shoppingCart:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['financinglink:shoppingCart:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['financinglink:shoppingCart:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['financinglink:shoppingCart:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="shoppingCartList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="编号" align="center" prop="id" v-if="false" />
        <el-table-column label="用户编号" align="center" prop="userId" />
        <el-table-column label="规格库存编号" align="center" prop="specificationId" />
        <el-table-column label="购买数量" align="center" prop="number" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['financinglink:shoppingCart:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['financinglink:shoppingCart:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改购物车对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="shoppingCartFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户编号" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户编号" />
        </el-form-item>
        <el-form-item label="规格库存编号" prop="specificationId">
          <el-input v-model="form.specificationId" placeholder="请输入规格库存编号" />
        </el-form-item>
        <el-form-item label="购买数量" prop="number">
          <el-input v-model="form.number" placeholder="请输入购买数量" />
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

<script setup name="ShoppingCart" lang="ts">
import { listShoppingCart, getShoppingCart, delShoppingCart, addShoppingCart, updateShoppingCart } from '@/api/financinglink/shoppingCart';
import { ShoppingCartVO, ShoppingCartQuery, ShoppingCartForm } from '@/api/financinglink/shoppingCart/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const shoppingCartList = ref<ShoppingCartVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const shoppingCartFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: ShoppingCartForm = {
  userId: undefined,
  specificationId: undefined,
  number: undefined
}
const data = reactive<PageData<ShoppingCartForm, ShoppingCartQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: undefined,
    userId: undefined,
    specificationId: undefined,
    number: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询购物车列表 */
const getList = async () => {
  loading.value = true;
  const res = await listShoppingCart(queryParams.value);
  shoppingCartList.value = res.rows;
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
  shoppingCartFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: ShoppingCartVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加购物车";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: ShoppingCartVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getShoppingCart(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改购物车";
}

/** 提交按钮 */
const submitForm = () => {
  shoppingCartFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateShoppingCart(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addShoppingCart(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: ShoppingCartVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除购物车编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delShoppingCart(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('financinglink/shoppingCart/export', {
    ...queryParams.value
  }, `shoppingCart_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
