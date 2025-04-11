<template>
  <div class="main">
    <!-- 左侧 -->
    <div style="margin-top: 15px; margin-left: 80px; background-color: #ffffff; width: 1150px; ">
      <div
        style="margin-top: 20px; margin-left: 20px; flex-direction: column; justify-content: center;  height: 100%; text-align: center;">
        <div class="search-box" style="margin-bottom: 20px;">
          <div
            style="display: flex; background-color: #ffffff; align-items: center; width: 600px; height: 50px;  border: 1px solid #ccc;">
            <!-- 左侧搜索图标 -->
            <div style="display: flex; align-items: center; padding-left: 15px;">
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
        <div style="margin-left: -90%; font-size: 13px; margin-top: -10px; margin-bottom: 30px;">热门搜索:</div>
        <div style="display: flex; margin-left: 20px; margin-bottom: 10px;" @click="chooseGood(item.id)"
          v-for="item in goodsList" :key="item.id" class="zoom-container">
          <img height="150px" width="150px" :src="item.homePictureUrl ? item.homePictureUrl : item.homePicture"
            :alt="item.title">
          <div style="margin-left: 20px;">
            {{ item.type == 1 ? '[供]' : '[需]' }}{{ item.title }}
            <div class="price">
              ¥<span style="margin: 0 4px;">{{ item.univalence }}</span>/
              <span class="unit">{{ fl_goods_unit.find(flu => flu.value == item.unit).label }}</span>
              <div style="margin-top: 10px; font-size: 10px;color: #181818;">
                {{ item.createByNickname }}|{{ item.updateTime }}
              </div>
              <div style="margin-left: -10px;" class="address">
                {{ item.addressCode ? processedString(item.addressCode) : item.addressDetail }}
              </div>
            </div>
          </div>
        </div>


      </div>

    </div>
    <!-- 右侧 -->
    <div
      style="margin-top: 15px; margin-left: 10px; background-color: #ffffff; width: 440px;  display: flex; flex-direction: column;  align-items: center; gap: 10px;">
      <div style="margin-top: 20px; height: 35px; width: 140px; color: #ffffff; background-color: #99b63a; 
         display: flex; justify-content: center; align-items: center; cursor: pointer; 
         transition: background-color 0.3s;" @click="router.push(`/setting?type=1`);">
        免费发布信息
      </div>

      <div style=" margin-bottom: 10px; font-size: 20px; font-weight: bold;">近期热门信息</div>

      <!-- 购物列表 -->
      <div v-if="goodsList.length === 0">暂无数据</div>
      <div @click="chooseGood(item.id)" v-for="(item, index) in goodsListHot" :key="item.id" class="item"
        style="margin-left: 5%; margin-right: 5%; font-size: 16px; margin-top: -13px; padding: 5px; width: 90%; text-align: left;">
        {{ index + 1 }}、 {{ item.title }}
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
  //获取热点
  getListHot();
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
  orderByType: 0,
  type: 1,
  title: null,
})
const goodsList = ref([]);
const goodsListHot = ref([]);
const total = ref(0);
const isLoadMore = ref(true)

/** 查询商品和需求热点列表 */
const getListHot = async () => {
  const res = await listGoods({
    pageNum: 1,
    pageSize: 40,
    varietyFirst: null,
    varietySecond: null,
    varietyThird: null,
    orderByType: null,
    type: 1,
    title: null,
    orderByType: 4,
  });
  goodsListHot.value = res.rows;
};

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

.item {
  transition: color 0.3s, text-decoration 0.3s;
  /* 添加平滑过渡 */
  cursor: pointer;
  /* 鼠标悬停时显示点击手型 */
}

.item:hover {
  color: #4CAF50;
  /* 鼠标悬停时文字变为绿色 */
  text-decoration: underline;
  /* 鼠标悬停时文字下方出现下划线 */
}

.zoom-container {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  /* 添加平滑过渡效果 */
  cursor: pointer;
  /* 让鼠标显示为点击状态 */
}

.zoom-container:hover {
  transform: scale(1.03);
  /* 整个容器放大1.1倍 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  /* 鼠标悬停时添加阴影效果 */
}

.zoom-container:active {
  transform: scale(1.05);
  /* 鼠标点击时稍微缩小，模拟按下效果 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.15);
  /* 点击时减少阴影效果 */
}
</style>
