<template>
  <div>银行用户页面</div>
  <el-button type="danger" @click="logout">注销</el-button>
</template>

<script setup>
import useUserStore from '@/store/modules/user';
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

<style lang="scss" scoped></style>
