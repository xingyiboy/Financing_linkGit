<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter"
      :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="标题" prop="title">
              <el-input v-model="queryParams.title" placeholder="请输入标题" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="一级品种" prop="variety">
              <el-select v-model="queryParams.varietyFirst" placeholder="请选择一级品种">
                <el-option v-for="item in firstVarietys" :key="item.id" :label="item.label"
                  :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="二级品种" prop="variety">
              <el-select v-model="queryParams.varietySecond" placeholder="请选择二级品种">
                <el-option v-for="item in secondVarietys2" :key="item.id" :label="item.label"
                  :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="三级品种" prop="variety">
              <el-select v-model="queryParams.varietyThird" placeholder="请选择三级品种">
                <el-option v-for="item in thirdVarietys2" :key="item.id" :label="item.label"
                  :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="品种" prop="variety">
              <el-input v-model="queryParams.variety" placeholder="请输入品种" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
                <el-option v-for="dict in fl_goods_status" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="类型" prop="type">
              <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
                <el-option v-for="dict in fl_goods_type" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="详细地址" prop="addressDetail">
              <el-input v-model="queryParams.addressDetail" placeholder="请选择发货详细地址" clearable
                @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="地址选择" prop="addressCode">
              <el-cascader size="large" :props="props1" clearable :options="pcaTextArr"
                v-model="queryParams.addressCode" placeholder="请选择地址" />
            </el-form-item>
            <el-form-item label="物流方式" prop="logistics">
              <el-input v-model="queryParams.logistics" placeholder="请输入物流方式" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="创建者" prop="createByNickName">
              <el-input v-model="queryParams.createByNickName" placeholder="请输入创建者名称" clearable
                @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="创建时间" prop="createTime">
              <el-date-picker clearable v-model="queryParams.createTime" type="date" value-format="YYYY-MM-DD"
                placeholder="请选择创建时间" />
            </el-form-item>
            <!-- <el-form-item label="更新时间" prop="createTime">
              <el-date-picker clearable v-model="queryParams.updateTime" type="date" value-format="YYYY-MM-DD"
                placeholder="请选择更新时间" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd"
              v-hasPermi="['financinglink:goods:add']">新增</el-button>
          </el-col>
          <!-- <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()"
              v-hasPermi="['financinglink:goods:edit']">修改</el-button>
          </el-col> -->
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()"
              v-hasPermi="['financinglink:goods:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport"
              v-hasPermi="['financinglink:goods:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="goodsList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <!-- <el-table-column label="编号" align="center" prop="id" v-if="true" /> -->
        <el-table-column label="标题" align="center" prop="title" />
        <!-- <el-table-column label="详细内容" align="center" prop="content" /> -->
        <el-table-column label="首页图片" align="center" prop="homePictureUrl" width="100">
          <template #default="scope">
            <image-preview :src="scope.row.homePictureUrl" :width="50" :height="50" />
          </template>
        </el-table-column>
        <el-table-column label="品种" align="center" prop="variety" />
        <!-- <el-table-column label="图片" align="center" prop="pictureUrl" width="100">
          <template #default="scope">
            <image-preview :src="scope.row.pictureUrl" :width="50" :height="50" />
          </template>
        </el-table-column> -->
        <el-table-column label="单价" align="center" prop="univalence" />
        <el-table-column label="单价对应的单位" align="center" prop="unit">
          <template #default="scope">
            <dict-tag :options="fl_goods_unit" :value="scope.row.unit" />
          </template>
        </el-table-column>
        <el-table-column label="起售价(元)" align="center" prop="minimumPrice" />
        <el-table-column label="状态" align="center" prop="status">
          <template #default="scope">
            <dict-tag :options="fl_goods_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="类型" align="center" prop="type">
          <template #default="scope">
            <dict-tag :options="fl_goods_type" :value="scope.row.type" />
          </template>
        </el-table-column>
        <!-- <el-table-column label="发货详细地址" align="center" prop="addressDetail" /> -->
        <el-table-column label="地址" align="center" prop="addressCode" />
        <el-table-column label="物流方式" align="center" prop="logistics" />
        <el-table-column label="创建者" align="center" prop="createByNickname" />
        <el-table-column label="创建时间" align="center" prop="createTime">
          <template #default="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="更新者" align="center" prop="updateBy" /> -->
        <el-table-column label="更新时间" align="center" prop="updateTime">
          <template #default="scope">
            <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['financinglink:goods:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                v-hasPermi="['financinglink:goods:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改商品和需求对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="1000px" append-to-body>
      <el-form ref="goodsFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="详细内容">
          <editor v-model="form.content" :min-height="192" />
        </el-form-item>
        <el-form-item label="首页图片" prop="homePicture">
          <image-upload v-model="form.homePicture" />
        </el-form-item>
        <el-form-item label="一级品种" prop="variety">
          <el-select v-model="form.varietyFirst" placeholder="请选择一级品种">
            <el-option v-for="item in firstVarietys" :key="item.id" :label="item.label" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级品种" prop="variety">
          <el-select v-model="form.varietySecond" placeholder="请选择二级品种">
            <el-option v-for="item in secondVarietys" :key="item.id" :label="item.label" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="三级品种" prop="variety">
          <el-select v-model="form.varietyThird" placeholder="请选择三级品种">
            <el-option v-for="item in thirdVarietys" :key="item.id" :label="item.label" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图片" prop="picture">
          <image-upload v-model="form.picture" />
        </el-form-item>
        <el-form-item label="单价" prop="univalence">
          <el-input v-model="form.univalence" placeholder="请输入单价" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-select v-model="form.unit" placeholder="请选择单价对应的单位">
            <el-option v-for="dict in fl_goods_unit" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="起售价(元)" prop="minimumPrice">
          <el-input v-model="form.minimumPrice" placeholder="请输入起售价(元)" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in fl_goods_status" :key="dict.value" :value="parseInt(dict.value)">{{ dict.label
              }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option v-for="dict in fl_goods_type" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="addressCode">
          <el-cascader size="large" :options="pcaTextArr" :props="props1" clearable v-model="form.addressCode"
            placeholder="请选择地址" />
        </el-form-item>
        <el-form-item label="详细地址" prop="addressDetail">
          <el-input v-model="form.addressDetail" placeholder="请输入详细地址" />
        </el-form-item>

        <el-form-item label="物流方式" prop="logistics">
          <el-input v-model="form.logistics" placeholder="请输入物流方式" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Goods" lang="ts">
import { listGoods, getGoods, delGoods, addGoods, updateGoods } from '@/api/financinglink/goods';
import { GoodsVO, GoodsQuery, GoodsForm } from '@/api/financinglink/goods/types';
import { pcaTextArr } from 'element-china-area-data';
import { listVarietys } from "@/api/financinglink/varietys";
import { VarietysVO, VarietysQuery, VarietysForm } from '@/api/financinglink/varietys/types';
import { computed, ref } from 'vue';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { fl_goods_unit, fl_goods_type, fl_goods_status } = toRefs<any>(proxy?.useDict('fl_goods_unit', 'fl_goods_type', 'fl_goods_status'));

const goodsList = ref<GoodsVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const goodsFormRef = ref<ElFormInstance>();

const VarietyLists = ref<VarietysVO[]>([]);
//获取品种树
const getVarietys = async () => {
  const res = await listVarietys();
  VarietyLists.value = res.data;
}

// 计算一级品种
const firstVarietys = computed(() => {
  const newVarietys = []
  VarietyLists.value.forEach(item => {
    if (item.parentId === 0) {
      newVarietys.push(item)
    }
  })
  return newVarietys;
});
// 计算二级品种
const secondVarietys = computed(() => {
  const newVarietys = []
  VarietyLists.value.forEach(item => {
    if (item.parentId === form.value.varietyFirst) {
      newVarietys.push(item)
    }
  })
  return newVarietys;
});
// 计算二级品种2
const secondVarietys2 = computed(() => {
  const newVarietys = []
  VarietyLists.value.forEach(item => {
    if (item.parentId === queryParams.value.varietyFirst) {
      newVarietys.push(item)
    }
  })
  return newVarietys;
});
// 计算三级品种
const thirdVarietys = computed(() => {
  const newVarietys = []
  VarietyLists.value.forEach(item => {
    if (item.parentId === form.value.varietySecond) {
      newVarietys.push(item)
    }
  })
  return newVarietys;
});
// 计算三级品种2
const thirdVarietys2 = computed(() => {
  const newVarietys = []
  VarietyLists.value.forEach(item => {
    if (item.parentId === queryParams.value.varietySecond) {
      newVarietys.push(item)
    }
  })
  return newVarietys;
});

//选择任意一级选项
const props1 = {
  checkStrictly: true,
}

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: GoodsForm = {
  title: undefined,
  content: undefined,
  homePicture: undefined,
  variety: undefined,
  picture: undefined,
  univalence: undefined,
  unit: undefined,
  minimumPrice: undefined,
  status: undefined,
  type: undefined,
  addressDetail: undefined,
  addressCode: undefined,
  logistics: undefined,
}
const data = reactive<PageData<GoodsForm, GoodsQuery>>({
  form: { ...initFormData },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    title: undefined,
    content: undefined,
    homePicture: undefined,
    variety: undefined,
    picture: undefined,
    univalence: undefined,
    unit: undefined,
    minimumPrice: undefined,
    status: undefined,
    type: undefined,
    addressDetail: undefined,
    addressCode: undefined,
    logistics: undefined,
    createBy: undefined,
    createTime: undefined,
    NickName: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询商品和需求列表 */
const getList = async () => {
  loading.value = true;
  const res = await listGoods(queryParams.value);
  goodsList.value = res.rows;
  goodsList.value.forEach(item => {
    item.variety = ''
    VarietyLists.value.forEach(item2 => {
      if (item2.id == item.varietyFirst) {
        item.variety += item2.label
      }
      if (item2.id == item.varietySecond) {
        item.variety += ',' + item2.label
      }
      if (item2.id == item.varietyThird) {
        item.variety += ',' + item2.label
      }
    })

  })
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
  goodsFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: GoodsVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加商品和需求";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: GoodsVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getGoods(_id);

  //取出地址码
  if (res.data.addressCode && res.data.addressCode != '') {
    res.data.addressCode = res.data.addressCode.split(',');
  }


  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改商品和需求";
}

/** 提交按钮 */
const submitForm = () => {
  goodsFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      form.value.addressCode = form.value.addressCode.join(',');
      if (form.value.id) {
        await updateGoods(form.value).finally(() => buttonLoading.value = false);
      } else {
        await addGoods(form.value).finally(() => buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: GoodsVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除商品和需求编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delGoods(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('financinglink/goods/export', {
    ...queryParams.value
  }, `goods_${new Date().getTime()}.xlsx`)
}

onMounted(async () => {
  await getVarietys();
  await getList();
});
</script>
