<template>
  <!-- 更多专家 -->
  <div v-if="moreSpecialist">
    <div @click="changeMore()" style="cursor: pointer; margin-left: 20px; color: #4587f3;">返回</div>
    <div
      style="display: flex; justify-content: center; align-items: center; flex-direction: column; height: 100%; margin-top: 20px;">

      <div class="search-box" style="margin-bottom: 20px;">
        <div
          style=" display: flex; background-color: #ffffff; align-items: center; width: 600px; height: 50px; border: 1px solid #ccc;">

          <!-- 左侧搜索图标 -->
          <div style="display: flex; align-items: center; padding-left: 15px;">
            <i class="iconfont icon-sousuo"></i>
          </div>

          <!-- 输入框 -->
          <el-input v-model="querySpecialistName" style="flex-grow: 1; height: 100%;" placeholder="">
          </el-input>

          <!-- 右侧搜索按钮 -->
          <el-button
            style="margin-right: 10px; height: 40px; width: 140px; font-size: 20px; color: #ffffff; background-color: #4ce0b5;"
            @click="getList">
            搜索
          </el-button>
        </div>
      </div>


    </div>

    <!-- 专家列表 -->
    <div v-for="item in specialistList" :key="item.id" class="container-item">
      <div class="profile-container">
        <img :src="item.avatarUrl" class="avatar">
        <div class="details-section">
          <div class="row-item">
            <div class="info-item">
              <span>专家姓名：{{ item.name }}</span>
            </div>
            <div class="info-item">
              <span>职称：{{ item.professional }}</span>
            </div>
          </div>
          <div class="row-item">
            <div class="info-item">
              <span>从事专业：{{ item.major }}</span>
            </div>
            <div class="info-item">
              <span>电话：{{ item.phone }}</span>
            </div>
          </div>
          <div class="row-item">
            <div class="info-item">
              <span>单位：{{ item.address }}</span>
            </div>
          </div>
        </div>
        <div class="button-section">
          <div @click="askQuestion(item)" class="ask-question-btn">向我提问</div>
          <div @click="bookAppointment(item)" class="book-appointment-btn">线下预约</div>
        </div>
      </div>
    </div>

  </div>
  <div v-if="!moreSpecialist" class="main">
    <!-- 左侧 -->
    <div style="margin-top: 15px; margin-left: 80px; background-color: #ffffff; width: 1050px; ">
      <div
        style="margin-top: 20px; margin-left: 10px; flex-direction: column; justify-content: center;  height: 100%; text-align: center;">
        <div class="search-box" style="margin-bottom: 20px;">
          <div
            style="margin-left: 20px; display: flex; background-color: #ffffff; align-items: center; width: 600px; height: 50px;  border: 1px solid #ccc;">
            <!-- 左侧搜索图标 -->
            <div style="display: flex; align-items: center; padding-left: 25px;">
              <i class="iconfont icon-sousuo"></i>
            </div>

            <!-- 输入框 -->
            <el-input v-model="queryParams.title" style="flex-grow: 1; height: 100%;" placeholder="">
            </el-input>


            <!-- 右侧搜索按钮 -->
            <el-button
              style="margin-right: 10px; height: 40px; width: 140px; font-size: 20px; color: #ffffff; background-color: #4ce0b5;"
              @click="getList">
              搜索
            </el-button>
          </div>
        </div>
        <div style="margin-left: -85%; font-size: 13px; margin-top: -10px; margin-bottom: 30px;">热门搜索:</div>
        <div @click="toggleCollapse(item.id)"
          style="display: flex; margin-left: 20px; height: 100px; margin-bottom: 10px; margin-right: 10px;"
          v-for="item in questionsAnswersList" :key="item.id" class="zoom-container">

          <div style="margin-left: 20px;">
            [问] {{ item.title }}
          </div>

          <div class="info-container">
            <div>提问者：{{ item.createByNickname }}<span style="margin-left: 20px"></span>专家：{{ item.specialistNickname }}
            </div>
          </div>

          <!-- 折叠内容区域 -->
          <div class="info-container2" v-show="item.isCollapsed">
            <div>问题：{{ item.content }}</div>
            <div>回答：{{ item.answer == null ? '暂无回答' : item.answer }}</div>
          </div>
        </div>
        <div style="margin-bottom: 700px;"></div>




      </div>

    </div>
    <!-- 右侧 -->
    <div
      style="margin-top: 15px; margin-left: 10px; background-color: #ffffff; width: 540px;  display: flex; flex-direction: column;  align-items: center; gap: 10px;">
      <div style="margin-top: 10px; height: 35px; width: 240px; color: #3f7eb8;
      font-size: 20px; font-weight:bold; margin-bottom: 5px;
         display: flex; justify-content: center; align-items: center;
         transition: background-color 0.3s;">
        在线问答<span style="color: #181818; margin-left: 5px; margin-right: 5px;">|</span>专家预约
      </div>
      <div style="height: 10px; width: 100%; background-color: #f2f2f2;"></div>
      <div style="display: flex; justify-content: space-between; width: 100%;">
        <div style="font-weight:bold; margin-left: 20px; font-size: 20px;">专家列表</div>
        <div @click="changeMore()" class="shubiao" style="font-size: 14px; margin-right:  20px; color: #3f7eb8;">更多专家>>
        </div>
      </div>

      <div style=" margin-bottom: 10px; font-size: 20px; font-weight: bold;"></div>

      <!-- 专家列表 -->
      <div v-for="item in specialistList" :key="item.id" class="item"
        style="margin-left: 5%; margin-right: 5%; font-size: 16px; margin-top: -13px; padding: 5px; width: 90%; text-align: left;">
        <div class="expert-info">
          <img :src="item.avatarUrl" width="100" class="avatar">
          <div class="details">
            <div class="detail-row">
              <div class="detail-item">
                <span>专家姓名：{{ item.name }}</span>
              </div>
              <div class="detail-item">
                <span>职称：{{ item.professional }}</span>
              </div>
            </div>
            <div class="detail-row">
              <div class="detail-item">
                <span>从事专业：{{ item.major }}</span>
              </div>
              <div class="detail-item">
                <span>电话：{{ item.phone }}</span>
              </div>
            </div>
            <div class="detail-row">
              <div class="detail-item">
                <span>单位：{{ item.address }}</span>
              </div>
            </div>
            <div class="action-buttons">
              <div @click="askQuestion(item)" class="ask-btn">去提问</div>
              <div @click="bookAppointment(item)" class="book-btn">去预约</div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
  <!-- 添加或修改预约对话框 -->
  <el-dialog title="预约" v-model="appointmentVisible" width="600px" append-to-body>
    <el-form ref="appointmentFormRef" :model="appointmentForm" :rules="rules" label-width="80px">
      <el-form-item label="地址" prop="address">
        <el-input v-model="appointmentForm.address" placeholder="请输入地址" />
      </el-form-item>
      <el-form-item label="种植作物" prop="crop">
        <el-input v-model="appointmentForm.crop" placeholder="请输入种植作物" />
      </el-form-item>
      <el-form-item label="作物详细" prop="cropDetail">
        <el-input v-model="appointmentForm.cropDetail" placeholder="请输入种植作物详细信息" />
      </el-form-item>
      <el-form-item label="作物条件" prop="cropCondition">
        <el-input v-model="appointmentForm.cropCondition" placeholder="请输入作物条件" />
      </el-form-item>
      <el-form-item label="土壤条件" prop="edaphicCondition">
        <el-input v-model="appointmentForm.edaphicCondition" placeholder="请输入土壤条件" />
      </el-form-item>
      <el-form-item label="面积" prop="area">
        <el-input v-model="appointmentForm.area" placeholder="请输入面积" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="appointmentForm.phone" placeholder="请输入电话" />
      </el-form-item>
      <el-form-item label="咨询者" prop="name">
        <el-input v-model="appointmentForm.name" placeholder="请输入咨询者姓名" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitAppointmentForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 添加或修改问答对话框 -->
  <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
    <el-form ref="questionsAnswersFormRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入标题" />
      </el-form-item>
      <el-form-item label="内容">
        <el-input v-model="form.content" placeholder="请输入内容" />
      </el-form-item>

      <el-form-item label="作物名称" prop="flName">
        <el-input v-model="form.flName" placeholder="请输入农作物名称" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入联系人手机号" />
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>



  <!-- 回顶部按钮 -->
  <!-- <div v-show="showBackToTop" @click="backToTop" class="back-to-top">回顶部</div> -->


</template>

<script setup name="Specialist" lang="ts">
import { ref, onMounted } from 'vue';
import { listSpecialist } from '@/api/financinglink/specialist';
import { listQuestionsAnswers, getQuestionsAnswers, delQuestionsAnswers, addQuestionsAnswers, updateQuestionsAnswers } from '@/api/financinglink/questionsAnswers';
import { QuestionsAnswersVO, QuestionsAnswersQuery, QuestionsAnswersForm } from '@/api/financinglink/questionsAnswers/types';
import { listAppointment, getAppointment, delAppointment, addAppointment, updateAppointment } from '@/api/financinglink/appointment';
import { AppointmentVO, AppointmentQuery, AppointmentForm } from '@/api/financinglink/appointment/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { fl_questions_status } = toRefs<any>(proxy?.useDict('fl_questions_status'));

const moreSpecialist = ref(false)

const toggleCollapse = (id) => {
  const item = questionsAnswersList.value.find(q => q.id == id);
  if (item) {
    item.isCollapsed = !item.isCollapsed;
  }
}

const changeMore = () => {
  querySpecialistName.value = ""
  getList();
  moreSpecialist.value = !moreSpecialist.value
}

const questionsAnswersList = ref<QuestionsAnswersVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const appointmentVisible = ref(false);
const appointmentForm = ref({
  address: null,
  crop: null,
  cropDetail: null,
  cropCondition: null,
  edaphicCondition: null,
  area: null,
  phone: null,
  name: null,
  specialist: null
})

const queryFormRef = ref<ElFormInstance>();
const questionsAnswersFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const appointmentFormRef = ref<ElFormInstance>();

/** 提交按钮 */
const submitAppointmentForm = () => {
  appointmentFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (appointmentForm.value.id) {
        await updateAppointment(appointmentForm.value).finally(() => buttonLoading.value = false);
      } else {
        await addAppointment(appointmentForm.value).finally(() => buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      appointmentVisible.value = false;
      await getList();
    }
  });
}

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
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);


/** 取消按钮 */
const cancel = () => {
  reset();
  dialog.visible = false;
  appointmentVisible.value = false;
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
  dialog.title = "提问";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: QuestionsAnswersVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getQuestionsAnswers(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改问答";
}

/** 提交按钮 */
const submitForm = () => {
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


const specialistList = ref(null);

const querySpecialistName = ref('');
/** 查询专家详情列表 */
const getList = async () => {
  const res2 = await listSpecialist({
    name: querySpecialistName.value
  });
  specialistList.value = res2.rows;
  loading.value = true;
  const res = await listQuestionsAnswers(queryParams.value);
  questionsAnswersList.value = res.rows;
  total.value = res.total;
  loading.value = false;
}

const askQuestion = (item) => {
  handleAdd();
  form.value.specialist = item.id
}
const bookAppointment = (item) => {
  appointmentForm.value = {
    address: null,
    crop: null,
    cropDetail: null,
    cropCondition: null,
    edaphicCondition: null,
    area: null,
    phone: null,
    name: null,
    specialist: null
  }
  appointmentForm.value.specialist = item.id
  appointmentVisible.value = true;
}



onMounted(() => {
  getList();
});
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  /* 使用 Flexbox 布局 */
  justify-content: left;
  /* 水平居中对齐 */
  padding: 0 5% 0 16%;
  /* 左右空出 40px */
  flex-wrap: wrap;
  /* 超出时换行 */
}

.varietys {
  font-size: 14px;
  margin: 13px;
  /* 每个元素之间的间距 */
}

.varietys:hover {
  color: rgb(33, 241, 33);
  /* 鼠标悬停时变为绿色 */
}

.total {
  display: flex;
  /* 使用 flexbox 布局 */
  justify-content: flex-end;
  /* 内容向右对齐 */
  align-items: baseline;
  /* 垂直对齐到基线 */
  text-align: right;
  /* 确保文本右对齐 */
  padding-right: 16%;
}

.total-number {
  color: red;
  /* 设置字体颜色为红色 */
  padding: 0 6px;
  /* 设置左右内边距 */
}

.main {
  min-width: 1200px;
  display: flex;
  // height: 100%;
  background-color: #f2f2f2;
}

.goods {
  box-sizing: border-box;
  /* 包括内边距和边框在内的总宽度 */
  width: calc(20% - 20px);
  /* 每个商品占宽度的20%，减去左右间距 */
  margin: 10px;
  /* 每个商品的间距 */
  background-color: #fff;
  /* 背景色 */
  // border: 1px solid #ddd;
  /* 边框 */
  // border-radius: 5px;
  /* 圆角 */
  overflow: hidden;
  /* 隐藏溢出的内容 */
  transition: transform 0.3s;
  /* 添加悬停时的过渡效果 */
}

.goods:hover {
  transform: scale(1.05);
  /* 悬停放大效果 */
}

.address {
  color: #999999;
  padding: 10px;
  /* 内容内边距 */
  font-size: 9px;
  /* 字体大小 */
}

.price {
  padding: 10px;
  font-size: 14px;
  text-align: left;
  /* 确保内容左对齐 */
}


.goods-container {
  display: flex;
  flex-wrap: wrap;
  /* 允许换行 */
  justify-content: flex-start;
  /* 均匀分布 */
  padding: 0 16%;
  /* 左右留出16%的空白 */
  box-sizing: border-box;
  /* 包括内边距和边框 */
}

.goods {
  box-sizing: border-box;
  /* 包括内边距和边框在内的总宽度 */
  width: calc(20% - 20px);
  /* 每个商品占宽度的20%，减去左右间距 */
  margin: 10px;
  /* 每个商品的间距 */
  background-color: #fff;
  /* 背景色 */
  // border: 1px solid #ddd;
  /* 边框 */
  // border-radius: 5px;
  /* 圆角 */
  overflow: hidden;
  /* 隐藏溢出的内容 */
  transition: transform 0.3s;
  /* 添加悬停时的过渡效果 */
}


.home-picture {
  width: 100%;
  /* 图片宽度100% */
  height: 210px;
  /* 高度自动 */
}

.goods-container {
  display: flex;
  flex-wrap: wrap;
  /* 允许换行 */
  justify-content: flex-start;
  /* 均匀分布 */
  padding: 0 16%;
  /* 左右留出16%的空白 */
  box-sizing: border-box;
  /* 包括内边距和边框 */
}



.title {
  font-weight: bold;
  /* 加粗 */
  display: -webkit-box;
  /* 使用盒子布局 */
  -webkit-box-orient: vertical;
  /* 纵向排列 */
  -webkit-line-clamp: 2;
  /* 限制为两行 */
  overflow: hidden;
  /* 隐藏超出部分 */
  text-overflow: ellipsis;
  /* 超出部分用省略号表示 */
  font-size: 16px;
  /* 字体大小 */
  margin-left: 6px;
  /* 内边距 */
}


.price {
  /* 垂直居中 */
  font-size: 20px;
  /* 字体大小 */
  color: red;
  /* 字体颜色 */
  margin: 0;
  /* 去掉默认边距 */
}

.unit {
  font-size: 12px;
  /* 单位字体变小 */
  color: red;
  /* 单位颜色为红色 */
}

.back-to-top {
  position: fixed;
  bottom: 140px;
  right: 220px;
  z-index: 1000;
  background-color: #f0f0f0;
  color: rgb(36, 184, 234);
  border: none;
  width: 50px;
  height: 50px;
  font-size: 14px;
  cursor: pointer;
  outline: none;
  transition: background-color 0.3s;

  display: flex;
  /* 使用 flexbox */
  align-items: center;
  /* 垂直居中 */
  justify-content: center;
  /* 水平居中 */

  &:hover {
    background-color: #22fdad;
  }
}

.navigation {
  display: flex;
  /* 使用 Flexbox 布局 */
  justify-content: left;
  /* 水平居中对齐 */
  padding: 0 5% 0 16.8%;
  /* 左右空出 40px */
  flex-wrap: wrap;
  /* 超出时换行 */
  margin-top: 10px;
  margin-bottom: 10px;
  font-size: 18px;
}

.active {
  color: rgb(33, 241, 33);
}

.activeOrder {
  color: rosybrown;
}

.search-box {
  :deep(.el-input__wrapper) {
    box-shadow: none;
    border-radius: none;
  }
}

.shubiao {
  transition: color 0.3s, text-decoration 0.3s;
  /* 添加平滑过渡 */
  cursor: pointer;
  /* 鼠标悬停时显示点击手型 */
}



.zoom-container {
  transition: transform 0.3s ease, box-shadow 0.03s ease;
  /* 添加平滑过渡效果 */
  cursor: pointer;
  /* 让鼠标显示为点击状态 */
}

.zoom-container:hover {
  transform: scale(1.001);
  /* 整个容器放大1.1倍 */
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  /* 鼠标悬停时添加阴影效果 */
}


.expert-info {
  font-size: 15px;
  display: flex;
  align-items: flex-start;
  padding: 5px;
  margin-bottom: 10px;
}

.avatar {
  margin-right: 20px;
}

.details {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
}

.detail-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  width: 48%;
  /* Ensure each item takes up roughly half of the row */
}


.action-buttons {
  display: flex;
  justify-content: flex-end;
}

.ask-btn,
.book-btn {
  margin-left: 10px;
  color: rgb(6, 239, 96);
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.zoom-container {
  position: relative;
  /* 设置相对定位，使得子元素可以进行绝对定位 */
}

.info-container {
  position: absolute;
  bottom: 0px;
  right: 10px;
  padding: 10px;
  font-size: 14px;
}

.info-container2 {
  position: absolute;
  bottom: 10px;
  left: 40px;
  /* 保持距离页面左边缘10px */
  padding: 10px;
  font-size: 17px;

  /* 添加边框 */

  /* 确保容器本身靠左对齐 */
  text-align: left;
  /* 控制内部文本的左对齐 */
}



.container-item {
  display: flex;
  margin: 10px 5%;
  font-size: 16px;
  padding: 5px;
  width: 90%;
  align-items: center;
  /* 使整行垂直居中 */
  text-align: left;
}

.profile-container {
  display: flex;
  width: 100%;
  justify-content: space-between;
  /* 两侧对齐，专家信息和按钮分开 */
  align-items: center;
  /* 垂直居中 */
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-right: 15px;
}

.details-section {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.row-item {

  display: flex;
  justify-content: flex-start;
  margin-bottom: 10px;
}

.info-item {
  margin-top: 5px;
  margin-left: 50px;
  margin-right: 20px;
  white-space: nowrap;
}

.info-item:last-child {
  margin-right: 0;
}

.button-section {
  display: flex;
  flex-direction: row;
  /* 水平排列按钮 */
  justify-content: flex-start;
  /* 左对齐 */
  gap: 10px;
  /* 按钮之间的间距 */
}

.ask-question-btn,
.book-appointment-btn {
  padding: 8px 16px;
  width: 100px;
  color: rgb(4, 239, 96);
  cursor: pointer;
}

.ask-question-btn:hover,
.book-appointment-btn:hover {
  color: #79ddb7;
}

.zoom-container {
  cursor: pointer;
}

.info-container {
  margin-top: 10px;
}

.zoom-container div {
  transition: all 0.3s ease;
}

.zoom-container .info-container {
  margin-bottom: 10px;
}

.zoom-container .info-container div {
  margin-bottom: 10px;
}
</style>
