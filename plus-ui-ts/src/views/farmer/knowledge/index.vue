<template>
  <div style="padding-left: 60px; margin-top: 30px; padding-right: 60px;" class="agr-knowledge-list">
    <div @click="router.push(`/knowledge/` + item.id)" v-for="item in agrKnowledgeList" :key="item.id"
      class="agr-knowledge-item">
      <img :src="item.homePictureUrl" alt="农业知识图片" class="image">
      <div class="content-wrapper">
        <div class="title">{{ item.title }}</div>
        <div class="content">{{ item.content }}</div>
      </div>
      <div class="footer">
        <div class="author">作者：{{ item.createByNickname }}</div>
        <div class="create-time">{{ item.createTime }}</div>
      </div>
    </div>
  </div>
</template>

<script setup name="AgrKnowledge" lang="ts">
import { listAgrKnowledgeOut, getAgrKnowledge, delAgrKnowledge, addAgrKnowledge, updateAgrKnowledge } from '@/api/financinglink/agrKnowledge';
import { AgrKnowledgeVO, AgrKnowledgeQuery, AgrKnowledgeForm } from '@/api/financinglink/agrKnowledge/types';
import router from '@/router';
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
  const res = await listAgrKnowledgeOut(queryParams.value);
  agrKnowledgeList.value = res.rows;

  agrKnowledgeList.value.forEach(function (item) {
    // 1. 提取所有 <p> 标签中的内容
    const matches = [...item.content.matchAll(/<p>(.*?)<\/p>/g)];

    // 2. 对每个 <p> 标签中的内容，去除 HTML 标签（通过正则删除标签）
    item.content = matches.map(match => match[1].replace(/<[^>]+>/g, '')).join(' ');  // 用空格连接段落
  });





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


<style scoped>
.agr-knowledge-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  padding: 20px;
}

.agr-knowledge-item {
  width: 780px;
  /* 固定宽度 */
  height: 350px;
  /* 固定高度 */
  display: flex;
  flex-direction: row;
  /* 将内容水平排列，图片在左，文本在右 */
  justify-content: flex-start;
  align-items: flex-start;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: relative;
}

.image {
  margin-top: 10px;
  width: 320px;
  /* 固定图片宽度 */
  height: 300px;
  /* 固定图片高度 */
  border-radius: 4px;
  margin-right: 15px;
  /* 图片与标题内容之间的间距 */
  object-fit: cover;
  /* 确保图片按比例填充 */
}

.content-wrapper {
  display: flex;
  flex-direction: column;
  /* 标题和内容垂直排列 */
  justify-content: flex-start;
  flex-grow: 1;
  /* 让内容部分占满剩余空间 */
  overflow: hidden;
  /* 防止内容溢出 */
}

.title {
  margin-top: 10px;
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
  white-space: nowrap;
  /* 防止标题溢出 */
  overflow: hidden;
  text-overflow: ellipsis;
  /* 超长文本显示省略号 */
}

.content {
  font-size: 14px;
  color: #666;
  line-height: 1.6;
  margin-bottom: 15px;
  word-wrap: break-word;
  /* 允许长单词换行 */
  white-space: normal;
  /* 允许内容换行 */
  overflow: visible;
  /* 让内容正常显示 */
}

.footer {
  position: absolute;
  bottom: 15px;
  /* 靠近底部 */
  right: 15px;
  /* 靠近右侧 */
  font-size: 12px;
  color: #888;
  /* display: flex; */
  flex-direction: row;
  gap: 10px;
  /* 让作者和时间之间有间距 */
}
</style>
