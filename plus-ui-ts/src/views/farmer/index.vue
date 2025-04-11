<template>
  <div class="main">

    <div class="dingbu">
      <div class="logo-container">
        <img class="logo" src="@/assets/images/logo.png" alt="融销通">
        <div class="text-container">
          <span class="h-title">融销通</span>
          <span>农产品融销一体平台</span>
        </div>
      </div>
    </div>

    <div class="daohang">
      <div style="height: 8%;"></div>
      <div style="display: flex; justify-content: center; margin-right: 10%; margin-top: 2px;">
        <span :class="['routerPath', router.currentRoute.value.path == item.path ? 'active' : '']"
          v-on:click="router.push(item.path)" v-for="item in menuList" :key="item.path">{{ item.name }}</span>
        <!-- 右侧的未登录和下拉菜单 -->
        <el-dropdown class="user-dropdown" trigger="click" style="position: absolute; margin-top: 4.9px; right: 20px;">
          <span @click="handleClick" style="color: #ffffff; font-size: 17px">
            {{ userName }}
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="router.push('/information')" :icon="Plus">个人信息</el-dropdown-item>
              <el-dropdown-item @click="logout" :icon="Plus">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>



    </div>
  </div>

  <div class="right-container">
    <router-view v-slot="{ Component }">
      <component :is="Component" ref="componentRef">
      </component>
    </router-view>
  </div>
</template>

<script setup>
import useUserStore from '@/store/modules/user';
import { ref } from 'vue';
import { ElMessageBox } from 'element-plus';
import { useRouter } from "vue-router";
import { getToken } from '@/utils/auth';
import { onMounted } from 'vue';
import { getInfo as getUserInfo } from '@/api/login';

const handleClick = () => {
  if (!isLogin.value) {
    router.push('/login');  // 如果未登录，则跳转到登录页
  }
};

const isLogin = ref(false);

const userName = ref("点击登录");

onMounted(async () => {
  //判断是否登录
  const res = getToken()
  if (res == null) {
    isLogin.value = false
  } else {
    isLogin.value = true
    const res2 = await getUserInfo()
    userName.value = res2.data.user.nickName
  }
});

const router = useRouter();

const menuList = ref([
  {
    name: "首页",
    path: "/home",
  },
  {
    name: "商品资源",
    path: "/resource",
  },
  {
    name: "求购需求",
    path: "/demand",
  },
  {
    name: "农业知识",
    path: "/knowledge",
  },
  {
    name: "专家指导",
    path: "/guidance",
  },
  {
    name: "购物车",
    path: "/shopping",
  }, {
    name: "融资申请",
    path: "/financing",
  },
  {
    name: "设置",
    path: "/setting",
  },
]);


const userStore = useUserStore();
const logout = async () => {
  await ElMessageBox.confirm('确定注销并退出系统吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  });
  await userStore.logout();
  location.href = import.meta.env.VITE_APP_CONTEXT_PATH + 'login';
};
</script>

<style lang="scss" scoped>
.h-title {
  color: #337849;
  font-weight: bold;
  /* 将文字设置为加粗 */
}

.logo-container {
  display: flex;
  align-items: center;
  /* 垂直居中对齐 */
}

.text-container {
  display: flex;
  flex-direction: column;
  /* 使文本垂直排列 */
}

.logo {
  margin-right: 10px;
  width: 50px;
  height: 50px;
  margin-left: 20px;
}

.dingbu {
  height: 55%;
}

.main {
  min-width: 1200px;
  width: 100%;
  height: 100px;
  background-color: #f0f0f0;
}

.right-container {
  width: 100%;
  height: calc(100vh - 100px);
  // background-color: #975353;
}

.daohang {
  height: 38%;
  background-color: #668e2f;
  // width: 100%;
  min-width: 1200px;
}

.routerPath {
  color: #fff;
  font-size: 18px;
  margin-left: 50px;
  padding-bottom: 8px;
}

.active {
  border-bottom: 2px solid #fff;
}

@media (max-width: 1036px) {
  .user-dropdown {
    display: none !important;
  }
}
</style>
