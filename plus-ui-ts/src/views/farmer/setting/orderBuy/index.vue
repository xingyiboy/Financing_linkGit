<template>
  <div style="width: 1400px;" class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter"
      :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <!-- <el-form-item label="编号" prop="id">
              <el-input v-model="queryParams.id" placeholder="请输入编号" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="用户编号" prop="userId">
              <el-input v-model="queryParams.userId" placeholder="请输入用户编号" clearable @keyup.enter="handleQuery" />
            </el-form-item> -->
            <!-- <el-form-item label="商品编号" prop="goodsId">
              <el-input v-model="queryParams.goodsId" placeholder="请输入商品编号" clearable @keyup.enter="handleQuery" />
            </el-form-item> -->
            <el-form-item class="el_form" label="商品标题" prop="title">
              <el-input v-model="queryParams.title" placeholder="请输入商品标题" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item class="el_form" label="单价" prop="univalence">
              <el-input v-model="queryParams.univalence" placeholder="请输入单价" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item class="el_form" label="购买数量" prop="number">
              <el-input v-model="queryParams.number" placeholder="请输入购买数量" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <!-- <el-form-item label="减少金额" prop="discountMoney">
              <el-input v-model="queryParams.discountMoney" placeholder="请输入优惠价减少金额" clearable
                @keyup.enter="handleQuery" />
            </el-form-item> -->
            <el-form-item class="el_form" label="花费金额" prop="money">
              <el-input v-model="queryParams.money" placeholder="请输入花费金额" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <!-- <el-form-item label="收货人" prop="name">
              <el-input v-model="queryParams.name" placeholder="请输入收货人姓名" clearable @keyup.enter="handleQuery" />
            </el-form-item> -->
            <el-form-item class="el_form" label="手机号" prop="phone">
              <el-input v-model="queryParams.phone" placeholder="请输入收货人手机号" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <!-- <el-form-item label="地址选择" prop="addressCode">
              <el-cascader size="large" :props="props1" clearable :options="pcaTextArr"
                v-model="queryParams.addressCode" placeholder="请选择地址" />
            </el-form-item> -->
            <el-form-item class="el_form" label="详细地址" prop="addressDetails">
              <el-input v-model="queryParams.addressDetails" placeholder="请输入详细地址" clearable
                @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item class="el_form" label="状态" prop="status">
              <el-select v-model="queryParams.status" placeholder="请选择状态">
                <el-option v-for="dict in fl_goods_order_status" :key="dict.value" :label="dict.label"
                  :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
            <!-- <el-form-item label="创建时间" prop="createTime">
              <el-date-picker clearable v-model="queryParams.createTime" type="date" value-format="YYYY-MM-DD"
                placeholder="请选择创建时间" />
            </el-form-item> -->
            <el-form-item>
              <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
              <el-button icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </transition>

    <el-card shadow="never">
      <template #header>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()"
              v-hasPermi="['financinglink:goodsOrder:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport"
              v-hasPermi="['financinglink:goodsOrder:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="goodsOrderList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="编号" width="106" align="center" prop="id" v-if="true" />
        <!-- <el-table-column label="用户编号" align="center" prop="userId" /> -->
        <!-- <el-table-column label="商品编号" align="center" prop="goodsId" /> -->
        <el-table-column label="商品标题" align="center" prop="title" />
        <el-table-column label="商品图" align="center" prop="homePictureUrl" width="100">
          <template #default="scope">
            <image-preview :src="scope.row.homePictureUrl" :width="50" :height="50" />
          </template>
        </el-table-column>
        <el-table-column label="单价" align="center" prop="univalence" />
        <el-table-column label="购买数量" width="50" align="center" prop="number" />
        <el-table-column label="优惠价减少金额" align="center" prop="discountMoney" />
        <el-table-column label="花费金额" align="center" prop="money" />
        <el-table-column label="收货人姓名" align="center" prop="name" />
        <el-table-column label="收货人手机号" align="center" prop="phone" />
        <el-table-column label="地址编号" align="center" prop="addressCode" />
        <el-table-column label="详细地址" align="center" prop="addressDetails" />
        <el-table-column label="状态" align="center" prop="status">
          <template #default="scope">
            <dict-tag :options="fl_goods_order_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <!-- <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column> -->
        <el-table-column width="110" label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-button @click="router.push(`/detail/${scope.row.goodsId}`);" style="width: 100%;">跳转至商品</el-button>
              </el-col>
              <el-col :span="24">
                <el-button v-if="scope.row.isComment == 1" @click="handleWatchComment(scope.row)"
                  style="width: 100%;">查看评价</el-button>
              </el-col>

              <el-col :span="24">
                <el-button v-if="scope.row.isComment == 0 && scope.row.status == 4" @click="handleComment(scope.row)"
                  style="width: 100%;">评价</el-button>
              </el-col>

              <el-col :span="24">
                <el-button v-if="scope.row.status == 2 || scope.row.status == 3" @click="handleUpdate(scope.row)"
                  style="width: 100%;">已收货</el-button>
              </el-col>

              <el-col :span="24">
                <el-button @click="handleDelete(scope.row)" v-hasPermi="['financinglink:goodsOrder:remove']"
                  style="width: 100%;">删除</el-button>
              </el-col>
            </el-row>

          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改商品订单对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="goodsOrderFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入商品标题" />
        </el-form-item>
        <el-form-item label="商品首页图" prop="homePicture">
          <image-upload v-model="form.homePicture" />
        </el-form-item>
        <el-form-item label="单价" prop="univalence">
          <el-input v-model="form.univalence" placeholder="请输入单价" />
        </el-form-item>
        <el-form-item label="购买数量" prop="number">
          <el-input v-model="form.number" placeholder="请输入购买数量" />
        </el-form-item>
        <el-form-item label="优惠价减少金额" prop="discountMoney">
          <el-input v-model="form.discountMoney" placeholder="请输入优惠价减少金额" />
        </el-form-item>
        <el-form-item label="花费金额" prop="money">
          <el-input v-model="form.money" placeholder="请输入花费金额" />
        </el-form-item>
        <el-form-item label="收货人姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入收货人姓名" />
        </el-form-item>
        <el-form-item label="收货人手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入收货人手机号" />
        </el-form-item>
        <el-form-item label="地址编号" prop="addressCode">
          <el-input v-model="form.addressCode" placeholder="请输入地址编号" />
        </el-form-item>
        <el-form-item label="详细地址" prop="addressDetails">
          <el-input v-model="form.addressDetails" placeholder="请输入详细地址" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog :title="commentTitle" v-model="commentVisible" width="500px" append-to-body>
      <el-form ref="commentFormRef" :rules="rules" label-width="80px">
        <el-form-item label="评论内容" prop="content">
          <el-input v-model="commentContent" type="textarea" placeholder="请输入内容" :readonly="commentTitle == '查看商品评价'" />
        </el-form-item>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitComment">确 定</el-button>
          <el-button @click="cancelComment">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="GoodsOrder" lang="ts">
import { listGoodsOrder, getGoodsOrder, delGoodsOrder, addGoodsOrder, updateGoodsOrder } from '@/api/financinglink/goodsOrder';
import { GoodsOrderVO, GoodsOrderQuery, GoodsOrderForm } from '@/api/financinglink/goodsOrder/types';
import { listGoodsComment, getGoodsComment, delGoodsComment, addGoodsComment, updateGoodsComment } from '@/api/financinglink/goodsComment';
import { GoodsCommentVO, GoodsCommentQuery, GoodsCommentForm } from '@/api/financinglink/goodsComment/types';
const { proxy } = getCurrentInstance() as ComponentInternalInstance;
import router from "@/router/index";
const { fl_goods_order_status } = toRefs<any>(proxy?.useDict('fl_goods_order_status'));

const goodsOrderList = ref<GoodsOrderVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const goodsOrderFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: GoodsOrderForm = {
  title: undefined,
  homePicture: undefined,
  univalence: undefined,
  number: undefined,
  discountMoney: undefined,
  money: undefined,
  name: undefined,
  phone: undefined,
  addressCode: undefined,
  addressDetails: undefined,
  status: undefined,
}
const data = reactive<PageData<GoodsOrderForm, GoodsOrderQuery>>({
  form: { ...initFormData },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: undefined,
    userId: undefined,
    type: 0,
    goodsId: undefined,
    title: undefined,
    homePicture: undefined,
    univalence: undefined,
    number: undefined,
    discountMoney: undefined,
    money: undefined,
    name: undefined,
    phone: undefined,
    addressCode: undefined,
    addressDetails: undefined,
    status: undefined,
    createTime: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

const commentTitle = ref('商品评价');
const commentVisible = ref(false);

const commentContent = ref('')
const commentOrderId = ref(null)
const commentGoodsId = ref(null)
const cancelComment = () => {
  commentVisible.value = false
}
//提交评论
const submitComment = async () => {
  if (commentTitle.value == '查看商品评价') {
    cancelComment();
    return;
  }
  const res = await addGoodsComment({
    goodsId: commentGoodsId.value,
    orderId: commentOrderId.value,
    content: commentContent.value
  });
  proxy?.$modal.msgSuccess("操作成功");
  getList();
  cancelComment();
}

// 查看商品评价
const handleWatchComment = async (row) => {
  const res = await listGoodsComment({
    goodsId: row.goodsId
  });
  console.log(res);

  commentContent.value = res.rows[0].content

  commentVisible.value = true
  commentTitle.value = '查看商品评价'
}

//添加商品评价
const handleComment = (row) => {
  commentOrderId.value = row.id
  commentGoodsId.value = row.goodsId
  commentVisible.value = true
  commentTitle.value = '添加商品评价'
}

/** 查询商品订单列表 */
const getList = async () => {
  loading.value = true;
  const res = await listGoodsOrder(queryParams.value);
  goodsOrderList.value = res.rows;
  total.value = res.total;
  loading.value = false;
}

/** 取消按钮 */
const cancel = () => {
  reset();
  dialog.visible = false;
}

/** 表单重置 */
const reset = () => {
  form.value = { ...initFormData };
  goodsOrderFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: GoodsOrderVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加商品订单";
}

/** 修改按钮操作 */
const handleUpdate = async (row) => {
  await proxy?.$modal.confirm('是否确认收货商品订单编号为"' + row.id + '"的数据项？').finally(() => loading.value = false);
  row.status = 4
  await updateGoodsOrder(row);
  proxy?.$modal.msgSuccess("操作成功");
  await getList();
}

/** 提交按钮 */
const submitForm = () => {
  goodsOrderFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateGoodsOrder(form.value).finally(() => buttonLoading.value = false);
      } else {
        await addGoodsOrder(form.value).finally(() => buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: GoodsOrderVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除商品订单编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delGoodsOrder(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('financinglink/goodsOrder/export', {
    ...queryParams.value
  }, `goodsOrder_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>

<style scoped>
.el_form {
  width: 300px;
}
</style>
