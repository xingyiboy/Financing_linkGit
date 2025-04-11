<template>
  <div class="bank-icons-container">
    <a v-for="(bank, index) in banks" :key="index" @click="DetailBtn(bank.name)">
      <img :src="bank.icon" :alt="bank.name">
    </a>
  </div>
</template>

<script>
export default {
  computed: {
    // 假设这里应该引用data中的banks，原代码中的bank未定义，已修正为banks
    banks() {
      return this.banks;
    }
  },
  data() {
    return {
      banks: [
        {
          name: '中国银行',
          icon: 'https://tse3-mm.cn.bing.net/th/id/OIP-C.2TRIeS2L4AUTMHhZDPEVqgHaHa?rs=1&pid=ImgDetMain',
        },
        {
          name: '中国工商银行',
          icon: 'https://img.51miz.com/Element/00/89/38/95/21c0d119_E893895_1cfe9bd4.png',
        },
        {
          name: '中国农业银行',
          icon: 'https://ts1.cn.mm.bing.net/th/id/R-C.d2e96f84f05bb016f30eabfaed0fba6d?rik=qsNr09h45FIZLg&riu=http%3a%2f%2fpic.baike.soso.com%2fp%2f20140212%2f20140212103228-1026863345.jpg&ehk=nTOedH9j2AfRf1KcXX%2fSuAWURrpppzoXmmtCKA1uiKQ%3d&risl=&pid=ImgRaw&r=0',
        },
        {
          name: '中国平安银行',
          icon: 'https://www.xuezishi.net/wp-content/uploads/2019/11/%E5%B9%B3%E5%AE%89%E9%93%B6%E8%A1%8Clogo-600x376.png',
        },
        // 其他银行数据...
      ]
    };
  },
  methods: {
    DetailBtn(bankName) {
      // 使用Vue Router进行路由跳转，跳转到名为bankDetail的路由，并传递银行名称作为参数
      this.$router.push({
        name: 'bankDetail',  //跳转的页面
        params: {
          bankName: bankName
        }
      });
    }
  }
}
</script>

<style scoped>
.bank-icons-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  gap: 1px;
}

a {
  text-decoration: none;
  margin: 100px;
  margin-right: 10px;
  /* 每张图片的右边距为20px */
  margin-left: 10px;
  padding: 2px;
}

img {
  width: 200px;
  height: 200px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.4);
}
</style>