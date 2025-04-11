<template>
  <div style="padding-top: 20px;"></div>
  <div
    style=" display: flex; flex-direction: column; align-items: center; justify-content: center; width: 1200px;  margin: 0 auto; border: 1px solid #ccc; padding: 20px;">
    <div style="text-align: left; width: 100%;">我的收货地址</div>
  </div>
  <div
    style="display: flex; flex-direction: column; align-items: center; justify-content: center; width: 1200px;  margin: 0 auto; border: 1px solid #ccc; padding: 20px; position: relative;">
    <div style="text-align: left; width: 100%;">收货人: {{ loginUserAddress.name }}</div>
    <div style="text-align: left; width: 100%;">收货地址: {{ loginUserAddress.addressCode }} 详细地址: {{
      loginUserAddress.addressDetail }}</div>
    <div style="text-align: left; width: 100%;">收货手机号: {{ loginUserAddress.phone }}</div>
    <!-- 使用绝对定位将按钮定位到右侧居中 -->
    <div style="position: absolute; right: 20px; bottom: 20px; display: flex; align-items: center;">
      <el-button @click="changeAddress()" type="success">更换地址</el-button>
    </div>
  </div>







  <!-- 放大图片展示 -->
  <div v-if="isImageModalVisible" class="image-modal" @click="closeModal">
    <img :src="selectedImage" class="modal-image" />
  </div>
  <div class="main">
    <!-- 提示信息 -->
    <div style="min-width: 1200px;" class="info">
      <div><input type="checkbox" @change="selectAll"></div>
      <div>商品信息</div>
      <div style="margin-right: -90px;">价格</div>
      <div style="margin-right: -250px;">数量</div>
    </div>

    <!-- 商品信息,对其提示信息 -->
    <div style="min-width: 1200px;" class="goods" v-for="(item, index) in shoppingCartList" :key="index">

      <div class="goods-content">
        <input type="checkbox" style="margin-right: 40px;" v-model="item.checked" @change="calculateTotalMoney">
        <!-- 图片展示部分 -->
        <div class="goods-image">
          <img style="height: 100px; cursor: pointer;"
            :src="item.specificationVo.homePictureUrl ? item.specificationVo.homePictureUrl : item.specificationVo.homePicture"
            @click="showImage(item.specificationVo.homePictureUrl ? item.specificationVo.homePictureUrl : item.specificationVo.homePicture)" />
        </div>
        <!-- 标题 -->
        <div class="goods-title">{{ item.goodsVo != null ? item.goodsVo.title : '' }}<br>规格:{{
          item.specificationVo.content
        }}
        </div>
        <!-- 价格 -->
        <div class="goods-price">
          <span>¥<span class="price-value">{{ item.specificationVo.univalence }}</span>/</span>
          <span class="unit">
            {{ fl_goods_unit.find(flu => flu.value == item.specificationVo.unit) ? fl_goods_unit.find(flu => flu.value
              == item.specificationVo.unit).label : '' }}
          </span>
        </div>
        <!-- 数量 -->
        <div class="goods-quantity">
          <button @click="decreaseQuantity(item)" class="quantity-button decrease">-</button>
          <span class="quantity">{{ item.number }}</span>
          <button @click="increaseQuantity(item)" style="margin-right: 20px;"
            class="quantity-button increase">+</button>
        </div>
        <!-- 删除该购物车商品 -->
        <div class="delete-button" @click="deleteShoppingCart(item)">删除</div>
      </div>
    </div>

    <div style="min-width: 1200px; margin-top: 30px;">
      <!-- 全选按钮 -->
      <input style="margin-left: 25px;" v-model="selectAllChecked" @change="selectAll" type="checkbox" /> 全选
      <!-- 总金额 -->
      <span style=" margin-left: 72%;">总金额: <span style="color: red;">{{ totalMoney }}</span></span>
      <span style=" margin-left: 12px;"><el-button style="margin-top: -5px;" type="danger"
          @click="submitOrder">提交订单</el-button></span>
    </div>
  </div>
  <div style=" height: 200px;">
  </div>
  <el-dialog title="更换地址" v-model="changeAddressVisible" width="800px" append-to-body>
    <!-- 使用表格展示地址列表 -->
    <el-table ref="singleTable" :data="loginUserAddressList" highlight-current-row @current-change="handleCurrentChange"
      style="width: 100%">
      <el-table-column type="index" width="50">
        <el-table-column label="收货人" prop="name"></el-table-column>
        <el-table-column label="地址" prop="addressCode"></el-table-column>
        <el-table-column label="详细地址" prop="addressDetail"></el-table-column>
        <el-table-column label="收货手机号" prop="phone"></el-table-column>
      </el-table-column>
    </el-table>

    <!-- 底部按钮 -->
    <template #footer>
      <div class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitAddress">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { listShoppingCart, delShoppingCart, updateShoppingCart } from '@/api/financinglink/shoppingCart';
import { listAddress } from '@/api/financinglink/address';
import { useUserStore } from '@/store/modules/user';
import { addGoodsOrder } from '@/api/financinglink/goodsOrder';
import { ref, reactive, getCurrentInstance, nextTick, toRefs, onMounted } from "vue";
import { getGoods } from '@/api/financinglink/out';

const userStore = useUserStore();
const router = useRouter();
const singleTable = ref(null);

import { computed } from "vue";

const isImageModalVisible = ref(false); // 控制大图弹窗的显示与隐藏
const selectedImage = ref(''); // 当前选择的图片
const totalMoney = ref(0)

const changeAddressVisible = ref(false)
const selectedAddress = ref(null);

// 选中地址
const handleCurrentChange = (selection) => {
  selectedAddress.value = selection
}
//确定
const submitAddress = () => {
  loginUserAddress.value = selectedAddress.value
  changeAddressVisible.value = false;
}

/** 取消按钮 */
const cancel = () => {
  changeAddressVisible.value = false;
}


//更换地址
const changeAddress = async () => {
  changeAddressVisible.value = await true
}

//用户地址列表
const loginUserAddressList = ref([]);

//用户默认地址
const loginUserAddress = ref({
  name: '',
  addressCode: '',
  addressDetail: '',
  phone: ''
});

const showImage = (imageUrl) => {
  selectedImage.value = imageUrl;
  isImageModalVisible.value = true;
}

const closeModal = () => {
  isImageModalVisible.value = false;
  selectedImage.value = '';
}


const selectAllChecked = ref(false);

const selectAll = (event) => {
  shoppingCartList.value.forEach(item => {
    item.checked = event.target.checked;
  });
  selectAllChecked.value = event.target.checked;
  calculateTotalMoney();
};

const calculateTotalMoney = () => {
  totalMoney.value = shoppingCartList.value.reduce((total, item) => {
    return total + (item.checked ? item.number * item.specificationVo.univalence : 0);
  }, 0).toFixed(2); // 保留两位小数
};



const { proxy } = getCurrentInstance();
const { fl_goods_unit } = toRefs(proxy?.useDict('fl_goods_unit'));

const goodsList = ref([]);
const shoppingCartList = ref([]);

//提交订单
const submitOrder = async () => {
  if (totalMoney.value == 0) {
    proxy?.$modal.msgError("请选择商品");
    return;
  }
  totalMoney.value = 0;
  shoppingCartList.value.forEach(async item => {
    if (item.checked) {
      const res = await addGoodsOrder({
        goodsId: item.goodsVo.id,
        title: item.goodsVo.title,
        homePicture: item.goodsVo.homePicture,
        univalence: item.specificationVo.univalence,
        number: item.number,
        discountMoney: 0,
        money: (item.specificationVo.univalence * item.number),
        name: loginUserAddress.value.name,
        phone: loginUserAddress.value.phone,
        addressCode: loginUserAddress.value.addressCode,
        addressDetails: loginUserAddress.value.addressDetail,
        status: 1,
        specId: item.specificationVo.id,
      })
      await delShoppingCart(item.id);
      await getList();
      if (res.code == 200) {
        proxy?.$modal.msgSuccess("下单成功");
      }
    }
  });

}

const decreaseQuantity = (item) => {
  if (item.number > 1) {
    item.number--;
    //更新购物车数据
    updateShoppingCart(item);
  } else {
    proxy?.$modal.msgError("不能再减了");
  }
  calculateTotalMoney();

}
const increaseQuantity = (item) => {
  if (item.number + 1 > item.specificationVo.inventory) {
    proxy?.$modal.msgError("已超过最大库存数量");
  } else {
    item.number++;
    updateShoppingCart(item);
  }
  calculateTotalMoney();

}

const getlistAddress = async () => {
  const res = await listAddress({
    pageNum: 1,
    pageSize: 999,
    isDefault: 1
  });
  const res2 = await listAddress({
    pageNum: 1,
    pageSize: 999
  });
  if (res.rows[0] != null) {
    loginUserAddress.value = res.rows[0];
  }
  loginUserAddressList.value = res2.rows;
}

/** 查询购物车列表 */
const getList = async () => {
  const res = await listShoppingCart({
    pageNum: 1,
    pageSize: 999,
  });
  shoppingCartList.value = res.rows;
  shoppingCartList.value.forEach(item => {
    const cleanString = item.specificationVo.replace(/\\/g, ''); // 去掉所有反斜杠
    item.specificationVo = JSON.parse(cleanString);
    item.checked = false; // 初始化每个商品的选中状态为未选中
  });
};

onMounted(() => {
  getList();
  getlistAddress();
});

const deleteShoppingCart = async (item) => {
  console.log(item);
  await proxy?.$modal.confirm('是否确认删除该购物信息？');
  await delShoppingCart(item.id);
  await getList();
  proxy?.$modal.msgSuccess("删除成功");
};

</script>

<style lang="scss" scoped>
.main {
  width: 70%;
  margin: 0 auto;
  background-color: #ffffff;
}

.info {
  margin-top: 40px;
  margin-bottom: 30px;
  height: 40px;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.info div {
  flex-basis: 0;
  flex-grow: 1;
}

.goods {
  padding: 0 20px;
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid #ddd;
  /* 商品之间分隔线 */
}

.goods-content {
  display: flex;
  align-items: center;
  padding: 10px 0;
  /* 上下内边距 */
}

.goods-image {
  flex-basis: 15%;
  /* 占比15% */
}

.goods-image img {
  max-width: 100%;
  height: auto;
}

.goods-title {
  flex-basis: 50%;
  /* 占比50% */
}

.goods-price {
  flex-basis: 25%;
  /* 占比25% */
  display: flex;
  align-items: center;
}

.goods-quantity {
  flex-basis: 10%;
  /* 占比10% */
  display: flex;
  align-items: center;
}

.delete-button {
  width: 45px;
  cursor: pointer;
  color: red;
  /* 删除按钮颜色 */
  padding-left: 10px;
  /* 左内边距 */
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

/* 放大图片弹窗的样式 */
.image-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  /* 半透明黑色背景 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-image {
  max-width: 90%;
  /* 限制大图最大宽度 */
  max-height: 90%;
  /* 限制大图最大高度 */
  object-fit: contain;
  /* 保证图片按比例显示 */
  cursor: pointer;
}
</style>