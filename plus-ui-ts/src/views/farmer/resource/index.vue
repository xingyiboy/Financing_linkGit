<template>
  <div class="main">
    <div style=" width: 100%; height: 215px; background-color: #41d2a9; margin-top: -7px;">
      <div
        style="display: flex; flex-direction: column; justify-content: center; align-items: center; height: 100%; text-align: center;">
        <!-- 第一行 -->
        <div style="margin-bottom: 20px; font-size: 40px; font-weight: bold; color: #ffffff;">轻松买卖农产品 海量产地一手货源</div>

        <!-- 第二行 -->
        <div class="search-box" style="margin-bottom: 20px;">
          <div
            style="display: flex; background-color: #ffffff; align-items: center; width: 600px; height: 50px; border-radius: 10px; border: 1px solid #ccc;">
            <!-- 左侧搜索图标 -->
            <div style="display: flex; align-items: center; padding-left: 15px;">
              <i class="iconfont icon-sousuo"></i>
            </div>

            <!-- 输入框 -->
            <el-input v-model="queryParams.title" style="flex-grow: 1; height: 100%; border-radius: 80px;"
              placeholder="请输入关键字">
            </el-input>


            <!-- 右侧搜索按钮 -->
            <el-button
              style="height: 100%; width: 100px; border-radius: 10px; color: #ffffff; background-color: #4ce0b5;"
              @click="getList">
              搜索
            </el-button>
          </div>



        </div>

        <!-- 第三行 -->
        <div style="margin-left: -540px; color: #ffffff;">热搜词</div>
      </div>


    </div>
    <div class="total"> 共<span class="total-number">{{ total }}</span>件相关产品</div>
    <!-- 品种导航 -->
    <el-breadcrumb class="navigation" separator=">">
      <el-breadcrumb-item><a @click="chooseVarietysBack(-1)">商品选择</a></el-breadcrumb-item>
      <el-breadcrumb-item @click="chooseVarietysBack(index)" v-for="(item, index) in varietysNavigation"
        :key="item.id"><a>{{
          item.label }}</a></el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 品种选择 -->
    <div v-show="varietys == 1" class="container"><a class="varietys" v-for="item in varietysFrist" :key="item.id"
        @click="chooseFirst(item.id, item.label)">{{ item.label }}</a>
    </div>
    <div v-show="varietys == 2" class="container"><a class="varietys" v-for="item in varietysSecond" :key="item.id"
        @click="chooseSecond(item.id, item.label)">{{ item.label }}</a>
    </div>
    <div v-show="varietys == 3" class="container"><a :class="['varietys', chooseVarietysId == 0 ? 'active' : '']"
        @click="chooseVarietysBack(1)">不限</a><a :class="['varietys', chooseVarietysId == item.id ? 'active' : '']"
        v-for="item in varietysThird" :key="item.id" @click="chooseThird(item.id, item.label)">{{
          item.label }}</a>
    </div>
    <!-- 地址导航 -->
    <el-breadcrumb class="navigation" separator=">">
      <el-breadcrumb-item><a @click="chooseCityBack(-1)">地址选择</a></el-breadcrumb-item>
      <el-breadcrumb-item @click="chooseCityBack(index)" v-for="(item, index) in CityNavigation" :key="index"><a>{{
        item.label }}</a></el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 地址选择 -->
    <div v-show="cityChoose == 1" class="container"><a class="varietys" v-for="(item, index) in pcaTextArr" :key="index"
        @click="chooseFirstCity(index)">{{ item.label }}</a>
    </div>
    <div v-show="cityChoose == 2" class="container"><a class="varietys" v-for="(item, index) in citySecond" :key="index"
        @click="chooseSecondCity(index)">{{ item.label }}</a>
    </div>
    <div v-show="cityChoose == 3" class="container"><a :class="['varietys', chooseCityId == '' ? 'active' : '']"
        @click="chooseCityBack(1)">不限</a><a :class="['varietys', chooseCityId == item.label ? 'active' : '']"
        v-for="(item, index) in cityThird" :key="index" @click="chooseThirdCity(index)">{{ item.label }}</a>
    </div>
    <!-- 排序类型选择 -->
    <div class="container"><a :class="['varietys', queryParams.orderByType == item.value ? 'activeOrder' : '']"
        v-for="(item, index) in orderByTypeList" :key="index" @click="chooseOrderByType(item.value)">{{ item.label
        }}</a>
    </div>
    <div class="goods-container">
      <div @click="chooseGood(item.id)" v-for="item in goodsList" :key="item.id" class="goods">
        <img class="home-picture" :src="item.homePictureUrl ? item.homePictureUrl : item.homePicture" :alt="item.title">
        <div class="title">{{ item.title }}</div>

        <div class="price">
          ¥<span style="margin: 0 4px;">{{ item.univalence }}</span>/
          <span class="unit"> {{ fl_goods_unit.find(flu => flu.value == item.unit).label }}</span>
          <!-- <dict-tag class="unit" :options="" :value="item.unit" /> -->
          <div class="address">{{ item.addressCode ? processedString(item.addressCode) : item.addressDetail }}</div>
        </div>
      </div>
    </div>
  </div>

  <!-- 回顶部按钮 -->
  <div v-show="showBackToTop" @click="backToTop" class="back-to-top">回顶部</div>


</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue"
const { proxy } = getCurrentInstance();

import { onMounted } from 'vue';
import { listVarietys, listGoods } from "@/api/financinglink/out";
import router from "@/router/index";
import { pcaTextArr } from 'element-china-area-data';

const { fl_goods_unit } = toRefs(proxy?.useDict('fl_goods_unit'));

const inputValue = ref('')

const varietys = ref(1);
const cityChoose = ref(1);
//选择变色
const chooseVarietysId = ref(0);
const chooseCityId = ref('');

//排序类型列表
const orderByTypeList = ref([{
  label: '更新时间降序',
  value: 0
}, {
  label: '更新时间升序',
  value: 1
}, {
  label: '价格降序',
  value: 2
}, {
  label: '价格升序',
  value: 3
}, {
  label: '销量降序',
  value: 4
}, {
  label: '销量升序',
  value: 5
}])

const chooseOrderByType = async (value) => {
  queryParams.value.orderByType = value
  await getList()
}

//品种导航
const varietysNavigation = ref([]);
//城市导航
const CityNavigation = ref([]);

//城市选择
const citySecond = ref([]);
const cityThird = ref([]);

//一级地址选择
const chooseFirstCity = async (index) => {
  if (CityNavigation.value.length < 1) {
    CityNavigation.value.push({
      index: index,
      label: pcaTextArr[index].label
    })
  }
  chooseCityId.value = ''
  queryParams.value.addressCode = pcaTextArr[index].label
  queryParams.value.pageSize = 20
  isLoadMore.value = true;
  await getList()
  citySecond.value = pcaTextArr[index].children
  cityChoose.value = 2;
}
//二级地址选择
const chooseSecondCity = async (index) => {
  if (CityNavigation.value.length < 2) {
    CityNavigation.value.push({
      index: index,
      label: citySecond.value[index].label
    })
  }
  chooseCityId.value = ''
  queryParams.value.addressCode = CityNavigation.value[0].label + ',' + citySecond.value[index].label
  queryParams.value.pageSize = 20
  isLoadMore.value = true;
  await getList()
  cityThird.value = citySecond.value[index].children
  cityChoose.value = 3;
}
//三级地址选择
const chooseThirdCity = async (index) => {
  if (CityNavigation.value.length < 3) {
    CityNavigation.value.push({
      index: index,
      label: cityThird.value[index].label
    })
  }
  chooseCityId.value = cityThird.value[index].label
  queryParams.value.addressCode = CityNavigation.value[0].label + ',' + CityNavigation.value[1].label + ',' + cityThird.value[index].label
  queryParams.value.pageSize = 20
  isLoadMore.value = true;
  await getList()
}

//重显地址选择
const processedString = (originalString) => {
  // 将字符串分割成数组
  const parts = originalString.split(',');
  if (parts[2]) {
    return parts[0].slice(0, 2) + parts[1].slice(0, 2) + parts[2];
  }
  if (parts[1]) {
    return parts[0].slice(0, 2) + parts[1].slice(0, 2);
  }
  if (parts[0]) {
    return parts[0].slice(0, 2);
  }

}

const showBackToTop = ref(false);

// 回顶部函数
const backToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  });
};


onMounted(async () => {
  window.addEventListener('scroll', handleScroll);
  //获取第一级品种树
  getVarietysFristList()
  //获取商品
  await getList()
});

//更新页面页数
const updatePageNum = () => {
  queryParams.value.pageSize += 20;
};

let isThrottled = false; // 用于标记是否正在节流

const handleScroll = async () => {
  showBackToTop.value = window.scrollY > 100;

  const { scrollTop, scrollHeight, clientHeight } = document.documentElement;
  if (scrollTop + clientHeight >= scrollHeight - 700) {
    if (isLoadMore.value && !isThrottled) {
      updatePageNum(); // 更新页码
      isThrottled = true; // 设置节流标记
      await getList(true); // 获取更多数据，传入 true 表示是加载更多

      setTimeout(() => {
        isThrottled = false; // 过了一定时间后，取消节流标记
      }, 400); // 这里的 200 是节流时间，可以根据需求调整
    }
  }
};



const chooseGood = (id) => {
  router.push(`/detail/${id}`);
}

const queryParams = ref({
  pageNum: 1,
  pageSize: 20,
  varietyFirst: null,
  varietySecond: null,
  varietyThird: null,
  orderByType: null,
  type: 1,
  title: null,
})
const goodsList = ref([]);
const total = ref(0);
const isLoadMore = ref(true)
/** 查询商品和需求列表 */
const getList = async () => {
  const res = await listGoods(queryParams.value);
  if (isLoadMore.value) {
    // 如果是加载更多，将新数据追加到现有数据中
    goodsList.value = res.rows;
  } else {
    //没有数据了
  }
  goodsList.value.length > res.total ? isLoadMore.value = false : isLoadMore.value = true;
  total.value = res.total;
};


const varietysFrist = ref([]);
const varietysSecond = ref([]);
const varietysThird = ref([]);

//品种选择
/** 获取第一级品种树 */
const getVarietysFristList = async () => {
  const res = await listVarietys({
    parentId: 0
  });
  varietysFrist.value = res.data;
}
//城市导航返回
const chooseCityBack = (index) => {
  if (index == -1) {
    queryParams.value.addressCode = null;
    CityNavigation.value = []
    cityChoose.value = 1
    chooseCityId.value = ''
    getList()
  }
  if (index == 0) {
    cityChoose.value = 2
    chooseVarietysId.value = ''
    const chooseBackBefore = CityNavigation.value[0]
    CityNavigation.value = []
    CityNavigation.value.push(chooseBackBefore)
    chooseFirstCity(CityNavigation.value[0].index)
  }
  if (index == 1) {
    cityChoose.value = 3
    chooseVarietysId.value = ''
    const chooseBackBefore = CityNavigation.value[0]
    const chooseBackBefore2 = CityNavigation.value[1]
    CityNavigation.value = []
    CityNavigation.value = [chooseBackBefore, chooseBackBefore2]
    chooseSecondCity(CityNavigation.value[1].index)
  }
}

//品种导航返回
const chooseVarietysBack = async (index) => {
  if (index == -1) {
    queryParams.value.varietyFirst = null;
    queryParams.value.varietySecond = null;
    queryParams.value.varietyThird = null;
    varietysNavigation.value = []
    varietys.value = 2;
    queryParams.value.pageSize = 20
    isLoadMore.value = true;
    await getList()
    varietys.value = 1
    chooseVarietysId.value = 0
  }
  if (index == 0) {
    chooseVarietysId.value = 0
    const chooseBackBefore = varietysNavigation.value[0]
    chooseFirst(varietysNavigation.value[0].id)
    varietysNavigation.value = []
    varietysNavigation.value.push(chooseBackBefore)

  }
  if (index == 1) {
    chooseVarietysId.value = 0
    const chooseBackBefore = varietysNavigation.value[0]
    const chooseBackBefore2 = varietysNavigation.value[1]
    chooseSecond(varietysNavigation.value[1].id)
    varietysNavigation.value = []
    varietysNavigation.value = [chooseBackBefore, chooseBackBefore2]
  }
}
/** 点击第一级品种 */
const chooseFirst = async (id, label) => {
  if (varietysNavigation.value.length < 1) {
    varietysNavigation.value.push({
      id: id,
      label: label
    })
  }

  const res = await listVarietys({
    parentId: id
  });
  queryParams.value.varietyFirst = id;
  queryParams.value.varietySecond = null;
  queryParams.value.varietyThird = null;
  varietysSecond.value = res.data;
  varietys.value = 2;
  queryParams.value.pageSize = 20
  isLoadMore.value = true;
  await getList()
}

/** 点击第二级品种 */
const chooseSecond = async (id, label) => {
  if (varietysNavigation.value.length < 2) {
    varietysNavigation.value.push({
      id: id,
      label: label
    })
  }
  const res = await listVarietys({
    parentId: id
  });
  queryParams.value.varietyFirst = null;
  queryParams.value.varietySecond = id;
  queryParams.value.varietyThird = null;
  varietysThird.value = res.data;
  varietys.value = 3;
  queryParams.value.pageSize = 20
  isLoadMore.value = true;
  await getList()
}
/**
 * 点击三级品种
 */
const chooseThird = async (id, label) => {
  chooseVarietysId.value = id;
  if (varietysNavigation.value.length > 2) {
    varietysNavigation.value[2] = {
      id: id,
      label: label
    }
  } else {
    varietysNavigation.value.push({
      id: id,
      label: label
    })
  }
  queryParams.value.varietyFirst = null;
  queryParams.value.varietySecond = null;
  queryParams.value.varietyThird = id;
  goodsList.value = [];
  queryParams.value.pageSize = 20;
  isLoadMore.value = true;
  await getList();
}

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
  /* 内容内边距 */
  font-size: 14px;
  /* 字体大小 */
}

.goods-container {
  min-width: 1200px;
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
  width: calc(231.3px - 20px);
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

.price-container {
  display: flex;
  /* 使用弹性布局 */
  justify-content: space-between;
  /* 两端对齐 */
  align-items: center;
  /* 垂直居中 */
  margin: 10px;
  /* 内边距 */
}

.price {
  display: inline-flex;
  /* 使用内联弹性布局 */
  align-items: center;
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
</style>
