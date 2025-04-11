<template>
  <div style="min-width: 1200px;">
    <div class="knowledge-detail">
      <h1 class="title">{{ agrKnowledgeDetail.title }}</h1>
      <div class="footer">
        <span class="author" style="margin-right: 20px; ">作者：{{ agrKnowledgeDetail.createByNickname
          }}</span>
        <span class="date">日期：{{ agrKnowledgeDetail.createTime }}</span>
      </div>
      <div class="content" v-html="agrKnowledgeDetail.content"></div>
    </div>
    <div style="margin-left: 20%; margin-right: 20%;"><el-input v-model="queryParams.content" class="el-input"
        type="textarea"></el-input>
      <el-button @click="submitForm" type="success" class="el-button">添加评论</el-button>
      <p style="margin-left: 1.5%; margin-bottom: 10px;">评论共{{ agrKnowCommentList.length }}条</p>
      <div v-for="item in agrKnowCommentList" :key="item.id" class="comment-item">
        <div class="content">{{ item.content }}</div>
        <div class="user-id">评论人：{{ item.userIdNickname }}</div>
        <div class="create-time">评论时间：{{ item.createTime }}</div>
      </div>
      <div style="height: 90px;"></div>
    </div>
  </div>
</template>


<script setup name="AgrKnowledge" lang="ts">
import { getAgrKnowledge } from '@/api/financinglink/agrKnowledge';
import { listAgrKnowComment, getAgrKnowComment, delAgrKnowComment, addAgrKnowComment, updateAgrKnowComment } from '@/api/financinglink/agrKnowComment';
import { AgrKnowCommentVO, AgrKnowCommentQuery, AgrKnowCommentForm } from '@/api/financinglink/agrKnowComment/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const agrKnowCommentList = ref<AgrKnowCommentVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const agrKnowCommentFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: AgrKnowCommentForm = {
  content: undefined,
}
const data = reactive<PageData<AgrKnowCommentForm, AgrKnowCommentQuery>>({
  form: { ...initFormData },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userId: undefined,
    agrKnowId: undefined,
    content: undefined,
    createTime: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询农业知识评价列表 */
const getList = async () => {
  loading.value = true;
  const res = await listAgrKnowComment(queryParams.value);
  agrKnowCommentList.value = res.rows;
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
  agrKnowCommentFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: AgrKnowCommentVO[]) => {
  ids.value = selection.map(item => item.userId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加农业知识评价";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: AgrKnowCommentVO) => {
  reset();
  const _userId = row?.userId || ids.value[0]
  const res = await getAgrKnowComment(_userId);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改农业知识评价";
}

/** 提交按钮 */
const submitForm = async () => {
  if (queryParams.value.content == null || queryParams.value.content == '') {
    proxy?.$modal.msgError("请输入评论内容");
  } else {
    await addAgrKnowComment({
      agrKnowId: queryParams.value.agrKnowId,
      content: queryParams.value.content
    }).finally(() => buttonLoading.value = false);
    queryParams.value.content = ''
    proxy?.$modal.msgSuccess("操作成功");
    await getList();
  }


}

/** 删除按钮操作 */
const handleDelete = async (row?: AgrKnowCommentVO) => {
  const _userIds = row?.userId || ids.value;
  await proxy?.$modal.confirm('是否确认删除农业知识评价编号为"' + _userIds + '"的数据项？').finally(() => loading.value = false);
  await delAgrKnowComment(_userIds);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('financinglink/agrKnowComment/export', {
    ...queryParams.value
  }, `agrKnowComment_${new Date().getTime()}.xlsx`)
}


const agrKnowledgeDetail = ref({})

onMounted(async () => {
  const route = useRoute();
  const id = route.params.id; // 获取路由参数
  const res = await getAgrKnowledge(id);
  queryParams.value.agrKnowId = id;
  agrKnowledgeDetail.value = res.data;
  getList();
});
</script>

<style scoped>
/* General reset and font setup */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: Arial, sans-serif;
  line-height: 1.6;
  background-color: #f4f4f4;
  color: #333;
}

/* Container for the knowledge detail */
.knowledge-detail {
  max-width: 800px;
  margin: 30px auto;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  /* box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); */
}

/* Title styling */
.title {
  font-size: 2em;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 20px;
  text-align: center;
}

/* Content section with padding */
.content {
  font-size: 1.1em;
  line-height: 1.8;
  margin-bottom: 20px;
  color: #555;
}

/* Image styling */
.image-container {
  text-align: center;
  margin-bottom: 20px;
}

.image-container img {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
}

/* Footer area for the author and date */
.footer {
  display: flex;
  justify-content: flex-end;
  /* 将内容对齐到右侧 */
  font-size: 0.9em;
  color: #777;
  border-top: 1px solid #eee;
  padding-top: 10px;
}

/* 添加评论部分 */
.el-input {
  width: 100%;
  height: 120px;
  /* 增加输入框的高度为原来的4倍 */
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 5px;
  /* border: 1px solid #ddd; */
  resize: none;
  /* 禁止输入框缩放 */
  box-sizing: border-box;
}

/* 添加评论按钮样式 */
.el-button {
  margin-left: 91%;
  /* background-color: #409EFF; */
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.el-button:hover {
  /* background-color: #66b1ff; */
}

/* 评论展示部分 */
.comment-list {
  margin-top: 20px;
}

/* 评论项样式 */
.comment-item {
  background-color: #f9f9f9;
  padding: 15px;
  margin-bottom: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  position: relative;
  /* 为了定位右下角内容 */
}

/* 评论内容 */
.comment-item .content {
  margin-left: 30px;
  font-size: 14px;
  line-height: 1.5;
  color: #333;
}

/* 创建人样式 */
.comment-item .user-id {
  /* font-weight: bold; */
  /* color: #409EFF; */
  position: absolute;
  bottom: 10px;
  /* 右下角距离底部10px */
  right: 230px;
  /* 右下角距离右边10px */
  font-size: 12px;
}

/* 创建时间样式 */
.comment-item .create-time {
  font-size: 12px;
  /* color: #aaa; */
  position: absolute;
  bottom: 10px;
  right: 20px;
  /* 创建时间稍微向左偏移 */
}
</style>
