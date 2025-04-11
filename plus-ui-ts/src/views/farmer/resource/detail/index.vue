<template>
  <div class="main">
    <div class="user-info">
      <img :src="goodsDetail.createByAvatar" class="avatar">
      <div class="user-details">
        <span class="username"><span style="text-decoration: underline;">{{ goodsDetail.createByNickname }}</span><span
            class="shop-link">进入店铺</span></span>
      </div>
    </div>

    <div style="min-width: 1200px;" class="content">
      <el-carousel class="picture" height="450px" indicator-position="outside" trigger="click">
        <el-carousel-item v-for="(item, index) in pictures" :key="index">
          <img :src="item" class="carousel-image" @load="updateCarouselSize">
        </el-carousel-item>
      </el-carousel>
      <div class="goods-info">
        <div class="title">{{ goodsDetail.title }}</div>
        <div class="additional-info">
          <div class="info-item"><span style="margin-top: 20px;" class="label">{{ isDemand ? '收购价格:'
            : '价格:'
              }}</span><span style="color: red;">
              <span class="value"><span>¥<span style="margin: 0 4px; font-size: 40px;">{{ goodsDetail.univalence
                    }}</span>/</span>
                <span class="unit">
                  {{ fl_goods_unit.find(flu => flu.value == goodsDetail.unit) ? fl_goods_unit.find(flu => flu.value ==
                    goodsDetail.unit).label : '' }}</span></span></span></div>
          <div v-if="!isDemand" class="info-item"><span class="label">起售价:</span> <span class="value">{{
            goodsDetail.minimumPrice
              }}元</span></div>
          <div class="info-item"><span class="label">{{ isDemand ? '收货地址:' : '产地:' }}</span> <span class="value">{{
            goodsDetail.addressCode }}</span>
          </div>
          <div class="info-item"><span class="label">详细地址:</span> <span class="value">{{ goodsDetail.addressDetail
              }}</span></div>
          <div class="info-item"><span class="label">{{ isDemand ? '收货方式:' : '物流:' }}</span> <span class="value">{{
            goodsDetail.logistics }}</span>
          </div>
        </div>
        <!-- 商品规格库存 -->
        <div style="margin-top: 20px;"></div>
        <div v-for="(item, index) in specifications" :key="index" class="specification-container">
          <div class="specification-content">
            <span class="specification-label">
              <img @click="openModal(item)" :src="item.homePictureUrl ? item.homePictureUrl : item.homePicture"
                class="item-image">
              <span class="item-content">{{ item.content }}</span>
            </span>
            <div class="price-controls">
              <span class="specification-price">
                <span>¥<span class="price-value">{{ item.univalence }}</span>/</span>
                <span class="unit">
                  {{ fl_goods_unit.find(flu => flu.value == item.unit) ? fl_goods_unit.find(flu => flu.value ==
                    item.unit).label : '' }}
                </span>
              </span>
              <div class="quantity-controls">
                <button @click="decreaseQuantity(index)" class="quantity-button decrease">-</button>
                <span class="quantity">{{ item.number }}</span>
                <button @click="increaseQuantity(index)" style="margin-right: 20px;"
                  class="quantity-button increase">+</button>
              </div>
            </div>
          </div>
        </div>
        <div style="margin-bottom: 40px;" class="button-container">
          <span @click="buyNow()" class="button buy-now">{{ isDemand ? '立即联系' : '立即购买' }}</span>
          <span @click="addToCart()" v-if="!isDemand" class="button add-to-cart">加入购物车</span>
        </div>
      </div>
    </div>
    <div class="content-wrapper">
      <!-- 栏目切换按钮 -->
      <div style="min-width: 1000px;" class="tab-nav">
        <button @click="currentTab = 0" :class="{ active: currentTab === 0 }">{{ isDemand ? '需求详情'
          : '商品详情' }}</button>
        <button v-if="!isDemand" @click="currentTab = 1" :class="{ active: currentTab === 1 }">商品评价</button>
      </div>

      <!-- 商品详情部分 -->
      <div style="min-width: 1200px;" v-if="currentTab === 0" class="content-section">
        <div style="margin-left: 70px; margin-top: 10px; margin-bottom: 30px;" v-html="goodsDetail.content"
          class="rich-text-content"></div>
        <img style="margin-left: 50px;" v-for="(item, index) in pictures" :key="index" :src="item" />
      </div>

      <!-- 商品评价部分 -->
      <div style="min-width: 1000px;" v-if="currentTab === 1" class="content-section">
        <div v-for="item in commentList" :key="item.id" class="comment-item">
          <img :src="item.avatar" alt="User Avatar">
          <div class="content">{{ item.content }}</div>
          <div class="userNickname">{{ item.userNickname }}</div>
          <div class="createTime">{{ item.createTime }}</div>
        </div>
        <p v-if="commentList.length == 0" style="margin-left: 40%; margin-top: 40%; margin-bottom: 100px;">暂无评价</p>
      </div>
    </div>
  </div>
  <!-- 商品推荐 -->
  <div class="recommend">
    <div v-for="item in goodsList" :key="item.id" class="recommend-item" @click="getGoodsDetail(item.id)">
      <div style="font-weight: bold; margin-bottom: 10px;">{{ isDemand ? '相关热点需求' : '相关热卖产品' }}</div>
      <img :src="item.homePictureUrl" style="width: 230px; height: 230px;" alt="">
      <div style="width: 230px;">{{ item.title }}</div>
      <div style="width: 230px;" class=" price-container">
        <div style="color: red; margin-top: 10px;">
          ¥<span style="margin: 0 4px; font-size: 26px;">{{ item.univalence }}</span>/
          <span class="unit"> {{ fl_goods_unit.find(flu => flu.value == item.unit).label }}</span>
        </div>
        <span class="address" style="margin-top: 10px; font-size: 12px; color: #666666;">{{
          processedString(item.addressCode) }}</span>
      </div>
      <div style="margin-bottom: 50px;"></div>
    </div>
  </div>
  <!-- 模态框 -->
  <div v-if="isModalOpen" class="modal" @click="closeModal">
    <div class="modal-content" @click.stop>
      <img :src="selectedItem.homePictureUrl ? selectedItem.homePictureUrl : selectedItem.homePicture"
        class="modal-image" />
    </div>
  </div>

  <!-- 回顶部按钮 -->
  <div v-show="showBackToTop" @click="backToTop" class="back-to-top">回顶部</div>

</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue"
const { proxy } = getCurrentInstance();
import router from "@/router/index";

import { onMounted } from 'vue';
import { getGoods, listGoods, getFlSpecificationList } from '@/api/financinglink/out';
import { addShoppingCart } from '@/api/financinglink/shoppingCart';
import { listGoodsCommentOut } from '@/api/financinglink/goodsComment';
import { listAddressOut } from '@/api/financinglink/address';
import { addGoodsOrder } from '@/api/financinglink/goodsOrder';
const { fl_goods_unit } = toRefs(proxy?.useDict('fl_goods_unit'));

const goodsList = ref([])
const commentList = ref([])
//获取评价
const getCommentList = async () => {
  const res = await listGoodsCommentOut({
    goodsId: goodsDetail.value.id,
  })
  commentList.value = res.rows
}

//用户地址
const loginUserAddress = ref({
  name: '',
  addressCode: '',
  addressDetail: '',
  phone: ''
});

const getlistAddress = async () => {
  const res = await listAddressOut({
    pageNum: 1,
    pageSize: 999,
    isDefault: 1
  });
  loginUserAddress.value = res.rows[0];
}

const buyNow = async () => {
  console.log(loginUserAddress.value);
  if (loginUserAddress.value == null) {
    proxy?.$modal.msgError("请先选择设置默认地址");
    return;
  }
  let addCart = false
  await specifications.value.forEach(item => {
    if (item.number > 0) {
      addCart = true
      addGoodsOrder({
        goodsId: goodsDetail.value.id,
        title: goodsDetail.value.title,
        homePicture: goodsDetail.value.homePicture,
        univalence: item.univalence,
        number: item.number,
        discountMoney: 0,
        money: (item.univalence * item.number),
        name: loginUserAddress.value.name,
        phone: loginUserAddress.value.phone,
        addressCode: loginUserAddress.value.addressCode,
        addressDetails: loginUserAddress.value.addressDetail,
        status: 1,
        specId: item.id,
      }).then((result) => {
        proxy?.$modal.msgSuccess("购买成功");
      })
        .catch((error) => {
          proxy?.$modal.msgError("请先登录");
        });
    }
  })
  if (!addCart) {
    proxy?.$modal.msgError("请选择购买数量");
  }

  specifications.value.forEach(item => {
    item.number = 0
  })
}

const addToCart = async () => {
  let addCart = false
  await specifications.value.forEach(item => {
    if (item.number > 0) {
      addCart = true
      addShoppingCart({
        specificationId: item.id,
        number: item.number
      }).then((result) => {
        proxy?.$modal.msgSuccess("加入购物车成功");
      })
        .catch((error) => {
          proxy?.$modal.msgError("请先登录");
        });
    }
  })
  if (!addCart) {
    proxy?.$modal.msgError("请选择购买数量");
  }

  specifications.value.forEach(item => {
    item.number = 0
  })
}

const isModalOpen = ref(false);
const selectedItem = ref(null);
// 打开模态框并选择当前图片
const openModal = (item) => {
  selectedItem.value = item;
  isModalOpen.value = true;
};

// 关闭模态框
const closeModal = () => {
  isModalOpen.value = false;
  selectedItem.value = null;
};

const decreaseQuantity = (index) => {
  if (specifications.value[index].number > 0) {
    specifications.value[index].number--;
  } else {
    proxy?.$modal.msgError("不能再减了");
  }

}
const increaseQuantity = (index) => {
  if (specifications.value[index].number + 1 > specifications.value[index].inventory) {
    proxy?.$modal.msgError("已超过最大库存数量");
  } else {
    specifications.value[index].number++;
  }

}

const specifications = ref([])

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


const handleScroll = async () => {
  showBackToTop.value = window.scrollY > 100;
};


/** 查询商品和需求列表 */
const getList = async () => {
  const res = await listGoods({
    pageNum: 1,
    pageSize: 20,
    varietyThird: goodsDetail.value.varietyThird,
    type: goodsDetail.value.type
  });
  goodsList.value = res.rows;
};

const route = useRoute();
const id = route.params.id; // 获取路由参数

const currentTab = ref(0)

const goodsDetail = ref({
  varietyThird: null,
  createByNickname: null,
  createByAvatar: null,
  content: null,
  addressDetail: null,
  logistics: null,
  addressCode: '',
  minimumPrice: null,
  title: '',
  univalence: null,
  unit: null,
});
const pictures = ref([]);

const isDemand = ref(false)

const getGoodsDetail = async (id) => {
  commentList.value = []
  const res = await getGoods(id);
  goodsDetail.value = res.data;
  if (goodsDetail.value.picture) {
    pictures.value = goodsDetail.value.picture.split(',');
  }
  if (goodsDetail.value.pictureUrl) {
    pictures.value = goodsDetail.value.pictureUrl.split(',');
  }
  goodsDetail.value.addressCode = goodsDetail.value.addressCode.replace(/,/g, '');
  if (goodsDetail.value.type == 0) {
    isDemand.value = true
  }
  const res2 = await getFlSpecificationList(id);
  specifications.value = res2.data
  specifications.value = specifications.value.map(item => ({
    ...item,
    number: 0, // 添加 number 属性
  }));
  backToTop()
  await getCommentList()
}

onMounted(async () => {
  window.addEventListener('scroll', handleScroll);
  await getlistAddress();
  await getGoodsDetail(id)
  await getList()

});
</script>
<style lang="scss" scoped>
.picture {
  width: 420px;
}

.carousel-image {
  width: 100%;
  // width: 420px;
  // height: 450px;
}

.main {
  padding-left: 15%;
  padding-right: 24%;
  background-color: #ffffff;
}

.user-info {
  min-width: 1200px;
  display: flex;
  /* 使用 flexbox 布局 */
  align-items: center;
  /* 垂直居中 */
  justify-content: flex-start;
  /* 水平左对齐 */
  // background-color: #f94a4a;
  /* 背景颜色 */
  height: 100px;
  /* 高度为 100px */
  padding: 0 20px;
  /* 添加左右内边距 */
  box-sizing: border-box;
  /* 使内边距不影响总宽度 */
  border: 1px solid rgba(0, 0, 0, 0.1);
  /* 添加淡淡的边框 */
  border-radius: 8px;
  /* 圆角边框 */
  transition: border 0.3s ease;
  /* 添加渐变效果 */
  margin-bottom: 20px;
}

.user-info:hover {
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.avatar {
  width: 45px;
  /* 头像宽度 */
  height: 45px;
  /* 头像高度 */
  border-radius: 50%;
  /* 使头像为圆形 */
  margin-right: 10px;
  /* 头像与文字之间的间距 */
}

.user-details {
  display: flex;
  /* 使用 flexbox 布局 */
  flex-direction: column;
  /* 垂直排列 */
}

.username-container {
  display: flex;
  /* 使用 flexbox 布局 */
  align-items: center;
  /* 垂直居中 */
}

.username {
  margin-left: 15px;
  margin-top: -15px;
  font-weight: bold;
  /* 加粗用户名 */
  // text-decoration: underline;
  /* 下划线 */
  margin-right: 10px;
  /* 与进入店铺的间距 */
}

.shop-link {
  // text-decoration: none;
  margin-left: 70px;
  background-color: #28a745;
  /* 绿色背景 */
  color: white;
  /* 字体颜色为白色 */
  padding: 5px 10px;
  /* 内边距 */
  border-radius: 4px;
  /* 圆角边框 */
  width: fit-content;
  /* 自适应宽度 */
  text-align: center;
  /* 文本居中 */
  transition: background-color 0.3s ease;
  /* 添加渐变效果 */
}

.shop-link:hover {
  background-color: #218838;
  /* 悬停时变色 */
}




.content {
  display: flex;
  align-items: flex-start;
  /* 垂直顶部对齐 */
}

.goods-info {
  margin-left: 5%;
  /* 留出空间与轮播图分隔 */
  flex-grow: 1;
  /* 占满可用空间 */
}

.title {
  width: 550px;
  font-size: 1.5em;
  font-weight: bold;
  margin-bottom: 10px;
}

.price-info {
  margin-bottom: 10px;
}

/* 使用Grid布局 */
.additional-info {
  display: grid;
  grid-template-columns: auto 1fr;
  /* 第一列自动宽度，第二列占用剩余空间 */
  gap: 10px;
  /* 列间距 */
}

.info-item {
  display: contents;
  /* 保持Grid子元素的布局 */
}

.label {
  font-weight: bold;
  /* 让标签加粗 */
}

.value {
  width: 425px;
  text-align: left;
  /* 让内容左对齐 */
}

.button-container {
  display: flex;
  margin-top: 100px;
  margin-left: 20px;
}

.button {
  width: 200px;
  height: 80px;
  background-color: #eb552b;
  color: #ffffff;
  font-size: 37px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
  transition: background-color 0.3s ease;
  /* 平滑过渡效果 */
  cursor: pointer;
  /* 鼠标悬停时显示手型光标 */
}

.button:hover {
  background-color: #f21111;
  /* 悬停时变色 */
}

.button:last-child {
  margin-right: 0;
  /* 最后一个按钮不添加右边距 */
}

.content-wrapper {
  max-width: 600px;
  /* 控制最大宽度 */
  // margin-left: 5%;
  /* 居中 */
  // padding: 20px;
  /* 内边距 */
  // border: 1px solid #ccc;
  /* 边框样式 */
  // border-radius: 8px;
  /* 圆角 */
  // box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  /* 阴影效果 */
  // background-color: #f9f9f9;
  /* 背景颜色 */
}

.rich-text-content {
  line-height: 1.6;
  /* 行高 */
}

.tab-nav {
  display: flex;
  margin-bottom: 20px;
}

.tab-nav button {
  flex: 1;
  padding: 10px;
  border: none;
  background-color: #f9f9f9;
  font-size: 16px;
  cursor: pointer;
  text-align: center;
}

.tab-nav button.active {
  background-color: #dcdcdc;
}

.tab-nav button:hover {
  background-color: #e0e0e0;
}

@media (max-width: 1594px) {
  .recommend {
    display: none !important;
  }
}

.recommend {
  width: 13%;
  position: absolute;
  /* 绝对定位 */
  right: 170px;
  /* 右侧距离 */
  top: 280px;
  /* 顶部距离，根据需要调整 */
  // width: 200px;
  /* 根据需要设置宽度 */
  // background-color: #f9f9f9;
  /* 推荐产品的背景颜色 */
  // padding: 15px;
  /* 推荐产品的内边距 */
  // box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  /* 添加阴影以增强视觉效果 */
  z-index: 10;
  /* 确保在其他内容上方 */
}

.price-container {
  display: flex;
  /* 启用 flexbox */
  justify-content: space-between;
  /* 在两端对齐内容 */
  align-items: center;
  /* 垂直居中对齐 */
}

.address {
  margin-left: auto;
  /* 将地址推到右侧 */
}

.back-to-top {
  position: fixed;
  bottom: 140px;
  right: 100px;
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

.specification-container {
  display: block;
  border: 1px solid #ddd;
  width: 500px;
  transition: box-shadow 0.3s ease;
}

.specification-container:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.specification-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.specification-label {
  display: flex;
  align-items: center;
}

.item-image {
  margin-left: 10px;
  width: 40px;
  height: 40px;
}

.item-content {
  padding-left: 20px;
  font-size: 16px;
  font-weight: bold;
}

.price-controls {
  display: flex;
  align-items: center;
}

.specification-price {
  color: red;
  font-size: 18px;
  margin-right: 40px;
  /* 添加右边距以间隔数量控制 */
}

.price-value {
  font-size: 25px;
  margin: 0 4px;
}

.unit {
  font-size: 11px;
}

.quantity-controls {
  display: flex;
  align-items: center;
}

.quantity-button {
  border: 1px solid #ddd;
  background-color: #f9f9f9;
  padding: 5px 10px;
  cursor: pointer;
}

.quantity {
  margin: 0 10px;
}


.item-image:hover {
  transform: scale(1.05);
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  position: relative;
}

.modal-image {
  max-width: 90%;
  max-height: 90%;
}

.modal:hover {
  cursor: pointer;
}

.comment-item {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
  display: flex;
  align-items: flex-start;
  background-color: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  position: relative;
  /* 使绝对定位的元素相对于评论项容器定位 */
}

.comment-item img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 15px;
}

.comment-item div {
  font-size: 14px;
  color: #333;
}

.comment-item .content {
  font-size: 16px;
  color: #555;
  margin-bottom: 8px;
}

.comment-item .userNickname {
  font-weight: bold;
  position: absolute;
  bottom: 20px;
  right: 10px;
  font-size: 14px;
}

.comment-item .createTime {
  font-size: 12px;
  color: #888;
  position: absolute;
  bottom: 5px;
  right: 10px;
}
</style>
