import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { GoodsOrderVO, GoodsOrderForm, GoodsOrderQuery } from '@/api/financinglink/goodsOrder/types';

/**
 * 查询商品订单列表
 * @param query
 * @returns {*}
 */

export const listGoodsOrder = (query?: GoodsOrderQuery): AxiosPromise<GoodsOrderVO[]> => {
  return request({
    url: '/financinglink/goodsOrder/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询商品订单详细
 * @param id
 */
export const getGoodsOrder = (id: string | number): AxiosPromise<GoodsOrderVO> => {
  return request({
    url: '/financinglink/goodsOrder/' + id,
    method: 'get'
  });
};

/**
 * 新增商品订单
 * @param data
 */
export const addGoodsOrder = (data: GoodsOrderForm) => {
  return request({
    url: '/financinglink/goodsOrder',
    method: 'post',
    data: data
  });
};

/**
 * 修改商品订单
 * @param data
 */
export const updateGoodsOrder = (data: GoodsOrderForm) => {
  return request({
    url: '/financinglink/goodsOrder',
    method: 'put',
    data: data
  });
};

/**
 * 删除商品订单
 * @param id
 */
export const delGoodsOrder = (id: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/goodsOrder/' + id,
    method: 'delete'
  });
};
