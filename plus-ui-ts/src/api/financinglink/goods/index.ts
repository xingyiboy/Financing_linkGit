import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { GoodsVO, GoodsForm, GoodsQuery } from '@/api/financinglink/goods/types';

/**
 * 查询商品和需求列表
 * @param query
 * @returns {*}
 */

export const listGoods = (query?: GoodsQuery): AxiosPromise<GoodsVO[]> => {
  return request({
    url: '/financinglink/goods/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询商品和需求详细
 * @param id
 */
export const getGoods = (id: string | number): AxiosPromise<GoodsVO> => {
  return request({
    url: '/financinglink/goods/' + id,
    method: 'get'
  });
};

/**
 * 新增商品和需求
 * @param data
 */
export const addGoods = (data: GoodsForm) => {
  return request({
    url: '/financinglink/goods',
    method: 'post',
    data: data
  });
};

/**
 * 修改商品和需求
 * @param data
 */
export const updateGoods = (data: GoodsForm) => {
  return request({
    url: '/financinglink/goods',
    method: 'put',
    data: data
  });
};

/**
 * 删除商品和需求
 * @param id
 */
export const delGoods = (id: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/goods/' + id,
    method: 'delete'
  });
};
