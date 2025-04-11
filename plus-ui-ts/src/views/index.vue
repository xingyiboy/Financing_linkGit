<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>融销通系统首页面板</h2>

      </el-col>
    </el-row>
    <el-divider />
  </div>
</template>

<script setup name="Index" lang="ts">
import { ref, onBeforeMount } from 'vue';
import { useUserStore } from '@/store/modules/user';
import { getInfo as getUserInfo } from '@/api/login';
const userStore = useUserStore();
const router = useRouter();
const goTarget = (url: string) => {
  window.open(url, '__blank');
};
// onBeforeMount(async () => {
//   const res = await getUserInfo();
//   if (res.data.roles[0] == 'fl_farmer') {
//     await router.push('/fl_farmer');
//   } else if (res.data.roles[0] == 'fl_specialist') {
//     await router.push('/fl_specialist');
//   } else if (res.data.roles[0] == 'fl_bank_user') {
//     await router.push('/fl_bank_user');
//   }
// });

</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: 'open sans',
  'Helvetica Neue',
  Helvetica,
  Arial,
  sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>
