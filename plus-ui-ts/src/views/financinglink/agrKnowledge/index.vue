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
            <el-form-item label="标题" prop="title">
              <el-input v-model="queryParams.title" placeholder="请输入标题" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
                <el-option v-for="dict in fl_goods_status" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="创建时间" prop="createTime">
              <el-date-picker clearable v-model="queryParams.createTime" type="date" value-format="YYYY-MM-DD"
                placeholder="请选择创建时间" />
            </el-form-item>
            <el-form-item label="创建人id" prop="createBy">
              <el-input v-model="queryParams.createBy" placeholder="请输入创建人id" clearable @keyup.enter="handleQuery" />
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
              v-hasPermi="['financinglink:agrKnowledge:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()"
              v-hasPermi="['financinglink:agrKnowledge:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()"
              v-hasPermi="['financinglink:agrKnowledge:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport"
              v-hasPermi="['financinglink:agrKnowledge:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="agrKnowledgeList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="编号" align="center" prop="id" v-if="true" />
        <el-table-column label="标题" align="center" prop="title" />
        <el-table-column label="内容" align="center" prop="content" />
        <el-table-column label="首页图片" align="center" prop="homePictureUrl" width="100">
          <template #default="scope">
            <image-preview :src="scope.row.homePictureUrl" :width="50" :height="50" />
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status">
          <template #default="scope">
            <dict-tag :options="fl_goods_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建人id" align="center" prop="createBy" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['financinglink:agrKnowledge:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                v-hasPermi="['financinglink:agrKnowledge:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改农业知识对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="agrKnowledgeFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192" />
        </el-form-item>
        <el-form-item label="首页图片" prop="homePicture">
          <image-upload v-model="form.homePicture" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in fl_goods_status" :key="dict.value" :value="parseInt(dict.value)">{{ dict.label
              }}</el-radio>
          </el-radio-group>
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

<script setup name="AgrKnowledge" lang="ts">
import { listAgrKnowledge, getAgrKnowledge, delAgrKnowledge, addAgrKnowledge, updateAgrKnowledge } from '@/api/financinglink/agrKnowledge';
import { AgrKnowledgeVO, AgrKnowledgeQuery, AgrKnowledgeForm } from '@/api/financinglink/agrKnowledge/types';
const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { fl_goods_status } = toRefs<any>(proxy?.useDict('fl_goods_status'));



const agrKnowledgeList = ref<AgrKnowledgeVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const agrKnowledgeFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: AgrKnowledgeForm = {
  title: undefined,
  content: undefined,
  homePicture: undefined,
  status: undefined,
}
const data = reactive<PageData<AgrKnowledgeForm, AgrKnowledgeQuery>>({
  form: { ...initFormData },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: undefined,
    title: undefined,
    content: undefined,
    homePicture: undefined,
    status: undefined,
    createTime: undefined,
    createBy: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询农业知识列表 */
const getList = async () => {
  loading.value = true;
  const res = await listAgrKnowledge(queryParams.value);
  agrKnowledgeList.value = res.rows;
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
  agrKnowledgeFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: AgrKnowledgeVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加农业知识";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: AgrKnowledgeVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getAgrKnowledge(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改农业知识";
}

/** 提交按钮 */
const submitForm = () => {
  agrKnowledgeFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateAgrKnowledge(form.value).finally(() => buttonLoading.value = false);
      } else {
        await addAgrKnowledge(form.value).finally(() => buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: AgrKnowledgeVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除农业知识编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delAgrKnowledge(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('financinglink/agrKnowledge/export', {
    ...queryParams.value
  }, `agrKnowledge_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
