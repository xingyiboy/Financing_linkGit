<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="编号" prop="id">
              <el-input v-model="queryParams.id" placeholder="请输入编号" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="商品编号" prop="goodsId">
              <el-input v-model="queryParams.goodsId" placeholder="请输入商品编号" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="对应的单位" prop="unit">
              <el-select v-model="queryParams.unit" placeholder="请选择对应的单位" clearable >
                <el-option v-for="dict in fl_goods_unit" :key="dict.value" :label="dict.label" :value="dict.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="单价" prop="univalence">
              <el-input v-model="queryParams.univalence" placeholder="请输入单价" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="说明" prop="content">
              <el-input v-model="queryParams.content" placeholder="请输入说明" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="库存" prop="inventory">
              <el-input v-model="queryParams.inventory" placeholder="请输入库存" clearable @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['financinglink:specification:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['financinglink:specification:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['financinglink:specification:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['financinglink:specification:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="specificationList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="编号" align="center" prop="id" v-if="true" />
        <el-table-column label="商品编号" align="center" prop="goodsId" />
        <el-table-column label="对应的单位" align="center" prop="unit">
          <template #default="scope">
            <dict-tag :options="fl_goods_unit" :value="scope.row.unit"/>
          </template>
        </el-table-column>
        <el-table-column label="单价" align="center" prop="univalence" />
        <el-table-column label="展示图片" align="center" prop="homePictureUrl" width="100">
          <template #default="scope">
            <image-preview :src="scope.row.homePictureUrl" :width="50" :height="50"/>
          </template>
        </el-table-column>
        <el-table-column label="说明" align="center" prop="content" />
        <el-table-column label="库存" align="center" prop="inventory" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['financinglink:specification:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['financinglink:specification:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改规格库存对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="specificationFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品编号" prop="goodsId">
          <el-input v-model="form.goodsId" placeholder="请输入商品编号" />
        </el-form-item>
        <el-form-item label="对应的单位" prop="unit">
          <el-select v-model="form.unit" placeholder="请选择对应的单位">
            <el-option
                v-for="dict in fl_goods_unit"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单价" prop="univalence">
          <el-input v-model="form.univalence" placeholder="请输入单价" />
        </el-form-item>
        <el-form-item label="展示图片" prop="homePicture">
          <image-upload v-model="form.homePicture"/>
        </el-form-item>
        <el-form-item label="说明" prop="content">
          <el-input v-model="form.content" placeholder="请输入说明" />
        </el-form-item>
        <el-form-item label="库存" prop="inventory">
          <el-input v-model="form.inventory" placeholder="请输入库存" />
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

<script setup name="Specification" lang="ts">
import { listSpecification, getSpecification, delSpecification, addSpecification, updateSpecification } from '@/api/financinglink/specification';
import { SpecificationVO, SpecificationQuery, SpecificationForm } from '@/api/financinglink/specification/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { fl_goods_unit } = toRefs<any>(proxy?.useDict('fl_goods_unit'));

const specificationList = ref<SpecificationVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const specificationFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: SpecificationForm = {
  goodsId: undefined,
  unit: undefined,
  univalence: undefined,
  homePicture: undefined,
  content: undefined,
  inventory: undefined
}
const data = reactive<PageData<SpecificationForm, SpecificationQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: undefined,
    goodsId: undefined,
    unit: undefined,
    univalence: undefined,
    homePicture: undefined,
    content: undefined,
    inventory: undefined,
    params: {
    }
  },
  rules: {
    goodsId: [
      { required: true, message: "商品编号不能为空", trigger: "blur" }
    ],
    unit: [
      { required: true, message: "对应的单位不能为空", trigger: "change" }
    ],
    univalence: [
      { required: true, message: "单价不能为空", trigger: "blur" }
    ],
    homePicture: [
      { required: true, message: "展示图片不能为空", trigger: "blur" }
    ],
    content: [
      { required: true, message: "说明不能为空", trigger: "blur" }
    ],
    inventory: [
      { required: true, message: "库存不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询规格库存列表 */
const getList = async () => {
  loading.value = true;
  const res = await listSpecification(queryParams.value);
  specificationList.value = res.rows;
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
  specificationFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: SpecificationVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加规格库存";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: SpecificationVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getSpecification(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改规格库存";
}

/** 提交按钮 */
const submitForm = () => {
  specificationFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateSpecification(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addSpecification(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: SpecificationVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除规格库存编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delSpecification(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('financinglink/specification/export', {
    ...queryParams.value
  }, `specification_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
