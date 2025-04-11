<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter"
      :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="编号" prop="id">
              <el-input v-model="queryParams.id" placeholder="请输入编号" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="父id" prop="parentId">
              <el-input v-model="queryParams.parentId" placeholder="请输入父id" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="值" prop="label">
              <el-input v-model="queryParams.label" placeholder="请输入值" clearable @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd()"
              v-hasPermi="['financinglink:varietys:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" plain icon="Sort" @click="handleToggleExpandAll">展开/折叠</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>
      <el-table ref="varietysTableRef" v-loading="loading" :data="varietysList" row-key="id"
        :default-expand-all="isExpandAll" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
        <el-table-column label="父id" prop="parentId" />
        <el-table-column label="值" align="center" prop="label" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['financinglink:varietys:edit']" />
            </el-tooltip>
            <el-tooltip content="新增" placement="top">
              <el-button link type="primary" icon="Plus" @click="handleAdd(scope.row)"
                v-hasPermi="['financinglink:varietys:add']" />
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                v-hasPermi="['financinglink:varietys:remove']" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加或修改品种树对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="varietysFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父id" prop="parentId">
          <el-tree-select v-model="form.parentId" :data="varietysOptions"
            :props="{ value: 'id', label: 'label', children: 'children' }" value-key="id" placeholder="请选择父id"
            check-strictly />
        </el-form-item>
        <el-form-item label="值" prop="label">
          <el-input v-model="form.label" placeholder="请输入值" />
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

<script setup name="Varietys" lang="ts">
import { listVarietys, getVarietys, delVarietys, addVarietys, updateVarietys } from "@/api/financinglink/varietys";
import { VarietysVO, VarietysQuery, VarietysForm } from '@/api/financinglink/varietys/types';

type VarietysOption = {
  id: number;
  label: string;
  children?: VarietysOption[];
}

const { proxy } = getCurrentInstance() as ComponentInternalInstance;;


const varietysList = ref<VarietysVO[]>([]);
const varietysOptions = ref<VarietysOption[]>([]);
const buttonLoading = ref(false);
const showSearch = ref(true);
const isExpandAll = ref(true);
const loading = ref(false);

const queryFormRef = ref<ElFormInstance>();
const varietysFormRef = ref<ElFormInstance>();
const varietysTableRef = ref<ElTableInstance>()

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});


const initFormData: VarietysForm = {
  id: undefined,
  parentId: undefined,
  label: undefined
}

const data = reactive<PageData<VarietysForm, VarietysQuery>>({
  form: { ...initFormData },
  queryParams: {
    id: undefined,
    parentId: undefined,
    label: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询品种树列表 */
const getList = async () => {
  loading.value = true;
  const res = await listVarietys(queryParams.value);
  const data = proxy?.handleTree<VarietysVO>(res.data, "id", "parentId");
  if (data) {
    varietysList.value = data;
    loading.value = false;
  }
}

/** 查询品种树下拉树结构 */
const getTreeselect = async () => {
  const res = await listVarietys();
  varietysOptions.value = [];
  const data: VarietysOption = { id: 0, label: '顶级节点', children: [] };
  data.children = proxy?.handleTree<VarietysOption>(res.data, "id", "parentId");
  varietysOptions.value.push(data);
}

// 取消按钮
const cancel = () => {
  reset();
  dialog.visible = false;
}

// 表单重置
const reset = () => {
  form.value = { ...initFormData }
  varietysFormRef.value?.resetFields();
}

/** 搜索按钮操作 */
const handleQuery = () => {
  getList();
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
}

/** 新增按钮操作 */
const handleAdd = (row?: VarietysVO) => {
  reset();
  getTreeselect();
  if (row != null && row.id) {
    form.value.parentId = row.id;
  } else {
    form.value.parentId = 0;
  }
  dialog.visible = true;
  dialog.title = "添加品种树";
}

/** 展开/折叠操作 */
const handleToggleExpandAll = () => {
  isExpandAll.value = !isExpandAll.value;
  toggleExpandAll(varietysList.value, isExpandAll.value)
}

/** 展开/折叠操作 */
const toggleExpandAll = (data: VarietysVO[], status: boolean) => {
  data.forEach((item) => {
    varietysTableRef.value?.toggleRowExpansion(item, status)
    if (item.children && item.children.length > 0) toggleExpandAll(item.children, status)
  })
}

/** 修改按钮操作 */
const handleUpdate = async (row: VarietysVO) => {
  reset();
  await getTreeselect();
  if (row != null) {
    form.value.parentId = row.parentId;
  }
  const res = await getVarietys(row.id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改品种树";
}

/** 提交按钮 */
const submitForm = () => {
  varietysFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateVarietys(form.value).finally(() => buttonLoading.value = false);
      } else {
        await addVarietys(form.value).finally(() => buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row: VarietysVO) => {
  await proxy?.$modal.confirm('是否确认删除品种树编号为"' + row.id + '"的数据项？');
  loading.value = true;
  await delVarietys(row.id).finally(() => loading.value = false);
  await getList();
  proxy?.$modal.msgSuccess("删除成功");
}

onMounted(() => {
  getList();
});
</script>
