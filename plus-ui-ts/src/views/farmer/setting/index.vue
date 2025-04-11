<template>
  <div class="main">
    <!-- 左侧 -->
    <div style=" margin-top: 15px; margin-left: 45px; background-color: #ffffff; width: 220px;  ">
      <div class="demo-collapse">
        <el-collapse v-model="defaultActiveName">
          <el-collapse-item style="margin-left: 23px;" name="1">
            <template #title>
              <div class="icon-text-container">
                <!-- 图标 -->
                <i class="iconfont icon-wodexinxi2"></i>
                <!-- 文字 -->
                <span class="title-text">我的信息</span>
              </div>
            </template>
            <div style="min-width: 1200px; cursor: pointer; margin-left: 10px; margin-top: 10px; margin-bottom: 10px;"
              @click="router.push('/information')">
              <div @click="activeName = 1.1" class="icon-text-container">
                <i class="iconfont icon-jibenxinxi" :class="{ 'active-icon': activeName === 1.1 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 1.1 }">基本信息</span>
              </div>
            </div>
            <div style="cursor: pointer; margin-left: 10px; margin-bottom: 10px;" @click="router.push('/address')">
              <div @click="activeName = 1.2" class="icon-text-container">
                <i class="iconfont icon-shouhuodizhi" :class="{ 'active-icon': activeName === 1.2 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 1.2 }">收货地址</span>
              </div>
            </div>
            <div style="cursor: pointer; margin-left: 10px; margin-bottom: 10px;" @click="router.push('/changePwd')">
              <div @click="activeName = 1.3" class="icon-text-container">
                <i class="iconfont icon-xiugaimima2" :class="{ 'active-icon': activeName === 1.3 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 1.3 }">修改密码</span>
              </div>
            </div>
          </el-collapse-item>
          <el-collapse-item @click="router.push('/issue')" class="notarrow" style="margin-left: 23px;" name="2">
            <template #title>
              <div @click="activeName = 2" class="icon-text-container">
                <i class="iconfont icon-wodedingdan" :class="{ 'active-icon': activeName === 2 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 2 }">我的发布</span>
              </div>
            </template>
          </el-collapse-item>
          <el-collapse-item style="margin-left: 23px;" name="3">
            <template #title>
              <div class="icon-text-container">
                <i class="iconfont icon-zhuanjia"></i>
                <span class="title-text">我的专家</span>
              </div>
            </template>
            <div style="cursor: pointer; margin-left: 10px; margin-top: 10px; margin-bottom: 10px;"
              @click="router.push('/questions')">
              <div @click="activeName = 3.1" class="icon-text-container">
                <i class="iconfont icon-wenda" :class="{ 'active-icon': activeName === 3.1 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 3.1 }">我的问题</span>
              </div>
            </div>
            <div style="cursor: pointer; margin-left: 10px; margin-bottom: 10px;"
              @click="router.push('/myappointment')">
              <div @click="activeName = 3.2" class="icon-text-container">
                <i class="iconfont icon-yuyue" :class="{ 'active-icon': activeName === 3.2 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 3.2 }">我的预约</span>
              </div>
            </div>
          </el-collapse-item>
          <el-collapse-item v-if="!loginIsSpecialist" @click="router.push('/specialist')" class="notarrow"
            style="margin-left: 23px;" name="3.4">
            <template #title>
              <div @click="activeName = 3.4" class="icon-text-container">
                <i class="iconfont icon-cb-zhuanjiayonghu" :class="{ 'active-icon': activeName === 3.4 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 3.4 }">申请成为专家</span>
              </div>
            </template>
          </el-collapse-item>
          <el-collapse-item v-if="loginIsSpecialist" style="margin-left: 23px;" name="3.5">
            <template #title>
              <div class="icon-text-container">
                <i class="iconfont icon-cb-zhuanjiayonghu"></i>
                <span class="title-text">专家管理</span>
              </div>
            </template>
            <div style="cursor: pointer; margin-left: 10px; margin-top: 10px; margin-bottom: 10px;"
              @click="router.push('/expertInformation')">
              <div @click="activeName = 3.6" class="icon-text-container">
                <i class="iconfont icon-jibenxinxi" :class="{ 'active-icon': activeName === 3.6 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 3.6 }">专家信息</span>
              </div>
            </div>
            <div style="cursor: pointer; margin-left: 10px; margin-top: 10px; margin-bottom: 10px;"
              @click="router.push('/answers')">
              <div @click="activeName = 3.7" class="icon-text-container">
                <i class="iconfont icon-wenda" :class="{ 'active-icon': activeName === 3.7 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 3.7 }">我的回答</span>
              </div>
            </div>
            <div style="cursor: pointer; margin-left: 10px; margin-bottom: 10px;" @click="router.push('/appointment')">
              <div @click="activeName = 3.8" class="icon-text-container">
                <i class="iconfont icon-yuyue" :class="{ 'active-icon': activeName === 3.8 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 3.8 }">预约信息</span>
              </div>
            </div>
            <div style="cursor: pointer; margin-left: 10px; margin-bottom: 10px;" @click="router.push('/myknowledge')">
              <div @click="activeName = 3.9" class="icon-text-container">
                <i class="iconfont icon-zhishi" :class="{ 'active-icon': activeName === 3.9 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 3.9 }">我的知识</span>
              </div>
            </div>
          </el-collapse-item>
          <el-collapse-item style="margin-left: 23px;" name="4">
            <template #title>
              <div class="icon-text-container">
                <i class="iconfont icon-wode-dingdan"></i>
                <span class="title-text">我的订单</span>
              </div>
            </template>
            <div style="cursor: pointer; margin-left: 10px; margin-top: 10px; margin-bottom: 10px;"
              @click="router.push('/orderBuy')">
              <div @click="activeName = 4.1" class="icon-text-container">
                <i class="iconfont icon-xiayoumaifang" :class="{ 'active-icon': activeName === 4.1 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 4.1 }">我买的</span>
              </div>
            </div>
            <div style="cursor: pointer; margin-left: 10px; margin-top: 10px; margin-bottom: 10px;"
              @click="router.push('/orderSell')">
              <div @click="activeName = 4.2" class="icon-text-container">
                <i class="iconfont icon-maichu" :class="{ 'active-icon': activeName === 4.2 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 4.2 }">我卖的</span>
              </div>
            </div>
          </el-collapse-item>
          <el-collapse-item @click="router.push('/myfinancing')" class="notarrow" style="margin-left: 23px;" name="5">
            <template #title>
              <div @click="activeName = 5" class="icon-text-container">
                <i class="iconfont icon-caidan_woderongzi" :class="{ 'active-icon': activeName === 5 }"></i>
                <span class="title-text" :class="{ 'active-text': activeName === 5 }">我的融资</span>
              </div>
            </template>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>
    <!-- 右侧 -->
    <div
      style="margin-top: 15px; margin-left: 10px; background-color: #ffffff; width: 1397px;  display: flex; flex-direction: column;  align-items: center; gap: 10px;">
      <router-view v-slot="{ Component }">
        <component :is="Component" ref="componentRef"></component>
      </router-view>
    </div>
  </div>
</template>

<script lang="ts" setup>
import useUserStore from '@/store/modules/user';
import { ref } from 'vue';
import { ElMessageBox } from 'element-plus';
import { useRouter } from "vue-router";
import { getToken } from '@/utils/auth';
import { isSpecialist } from '@/api/financinglink/specialist';
import { SpecialistVO, SpecialistQuery, SpecialistForm } from '@/api/financinglink/specialist/types';
import { onMounted } from 'vue';

const isLogin = ref(false);
const defaultActiveName = ref('1')
const router = useRouter();
// 获取查询参数 type
const type = router.currentRoute.value.query.type;
onMounted(() => {
  if (type == 0) {
    router.push('/address')
  } else if (type == 1) {
    router.push('/issue')
  } else {
    router.push('/information')
  }
  //判断是否登录
  const res = getToken()
  if (res == null) {
    isLogin.value = false
  } else {
    isLogin.value = true
  }
  getIsSpecialist();
});

const loginIsSpecialist = ref(false);
const getIsSpecialist = async () => {
  const res = await isSpecialist();
  if (res.data == null) {
    loginIsSpecialist.value = false;
  } else {
    loginIsSpecialist.value = true;
  }

}




const activeName = ref(1)
</script>

<style lang="scss" scoped>
.main {
  min-width: 1200px;
  display: flex;
  height: 100%;
  background-color: #f2f2f2;
}

.notarrow {
  :deep(.el-collapse-item__arrow) {
    display: none;
  }
}

:deep(.el-collapse-item__arrow) {
  transform: rotate(-270deg);
}

:deep(.el-collapse-item__header.is-active .el-collapse-item__arrow) {
  transform: rotate(270deg);
}

/* 图标和文字容器，使用 flexbox 排列 */
.icon-text-container {
  // min-width: 1200px;
  display: flex;
  /* 水平排列图标和文字 */
  align-items: center;
  /* 垂直居中对齐 */
  font-size: 16px;
  /* 设置字体大小 */
}

/* 图标样式 */
.icon-text-container i {
  font-size: 15px;
  /* 图标大小，根据需要调整 */
  margin-right: 8px;
  /* 图标颜色，可以根据设计需求调整 */
}

/* 文字样式 */
.title-text {
  font-size: 16px;
  /* 文字大小，与图标保持协调 */
  color: #333;
  /* 文字颜色 */
}

/* 默认状态下，文字和图标颜色 */
.title-text,
.iconfont {
  color: #000;
}

/* 选中时的浅绿色 */
.active-text,
.active-icon {
  color: #90ee90;
  /* 浅绿色 */
}
</style>
