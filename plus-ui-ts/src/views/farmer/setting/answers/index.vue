<template>
  <div style="width: 1400px;" class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter"
      :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="标题" prop="title">
              <el-input v-model="queryParams.title" placeholder="请输入标题" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
                <el-option v-for="dict in fl_questions_status" :key="dict.value" :label="dict.label"
                  :value="dict.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="作物名称" prop="flName">
              <el-input v-model="queryParams.flName" placeholder="请输入农作物名称" clearable @keyup.enter="handleQuery" />
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
              v-hasPermi="['financinglink:questionsAnswers:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="questionsAnswersList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <!-- <el-table-column label="编号" align="center" prop="id" v-if="true" /> -->
        <el-table-column label="标题" align="center" prop="title" />
        <el-table-column label="状态" align="center" prop="status">
          <template #default="scope">
            <dict-tag :options="fl_questions_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="内容" align="center" prop="content" />
        <el-table-column label="农作物名称" align="center" prop="flName" />
        <el-table-column label="手机号" align="center" prop="phone" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-button link type="primary" @click="handleDetail(scope.row)">详情</el-button>
            <el-button link type="primary" @click="handleUpdate(scope.row)" v-if="scope.row.status == 0"
              v-hasPermi="['financinglink:questionsAnswers:edit']">回答</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改问答对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="1000px" append-to-body>
      <el-form ref="questionsAnswersFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input disabled v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input disabled v-model="form.content" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="作物名称" prop="flName">
          <el-input disabled v-model="form.flName" placeholder="请输入农作物名称" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input disabled v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="回答">
          <el-input :disabled="watchDetail" v-model="form.answer" placeholder="请输入回答" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" v-if="!watchDetail" @click="submitForm">提交回答</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="QuestionsAnswers" lang="ts">
import { listQuestionsAnswers, getQuestionsAnswers, delQuestionsAnswers, addQuestionsAnswers, updateQuestionsAnswers } from '@/api/financinglink/questionsAnswers';
import { QuestionsAnswersVO, QuestionsAnswersQuery, QuestionsAnswersForm } from '@/api/financinglink/questionsAnswers/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { fl_questions_status } = toRefs<any>(proxy?.useDict('fl_questions_status'));

const questionsAnswersList = ref<QuestionsAnswersVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const watchDetail = ref(false);

const queryFormRef = ref<ElFormInstance>();
const questionsAnswersFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: QuestionsAnswersForm = {
  title: undefined,
  content: undefined,
  status: undefined,
  flName: undefined,
  answer: undefined,
}
const data = reactive<PageData<QuestionsAnswersForm, QuestionsAnswersQuery>>({
  form: { ...initFormData },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    title: undefined,
    content: undefined,
    status: undefined,
    flName: undefined,
    answer: undefined,
    createBy: 1,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询问答列表 */
const getList = async () => {
  loading.value = true;
  const res = await listQuestionsAnswers(queryParams.value);
  questionsAnswersList.value = res.rows;
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
  questionsAnswersFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: QuestionsAnswersVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加问答";
}

const handleDetail = async (row?: QuestionsAnswersVO) => {
  watchDetail.value = true
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getQuestionsAnswers(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "问答详情";
}
/** 修改按钮操作 */
const handleUpdate = async (row?: QuestionsAnswersVO) => {
  watchDetail.value = false
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getQuestionsAnswers(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "回答问答";
}

/** 提交按钮 */
const submitForm = () => {
  form.value.status = 1
  questionsAnswersFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateQuestionsAnswers(form.value).finally(() => buttonLoading.value = false);
      } else {
        await addQuestionsAnswers(form.value).finally(() => buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: QuestionsAnswersVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除问答编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delQuestionsAnswers(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('financinglink/questionsAnswers/export', {
    ...queryParams.value
  }, `questionsAnswers_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
