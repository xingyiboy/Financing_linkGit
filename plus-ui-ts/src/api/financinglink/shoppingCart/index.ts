import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { ShoppingCartVO, ShoppingCartForm, ShoppingCartQuery } from '@/api/financinglink/shoppingCart/types';

/**
 * 查询购物车列表
 * @param query
 * @returns {*}
 */

export const listShoppingCart = (query?: ShoppingCartQuery): AxiosPromise<ShoppingCartVO[]> => {
  return request({
    url: '/financinglink/shoppingCart/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询购物车详细
 * @param id
 */
export const getShoppingCart = (id: string | number): AxiosPromise<ShoppingCartVO> => {
  return request({
    url: '/financinglink/shoppingCart/' + id,
    method: 'get'
  });
};

/**
 * 新增购物车
 * @param data
 */
export const addShoppingCart = (data: ShoppingCartForm) => {
  return request({
    url: '/financinglink/shoppingCart',
    method: 'post',
    data: data
  });
};

/**
 * 修改购物车
 * @param data
 */
export const updateShoppingCart = (data: ShoppingCartForm) => {
  return request({
    url: '/financinglink/shoppingCart',
    method: 'put',
    data: data
  });
};

/**
 * 删除购物车
 * @param id
 */
export const delShoppingCart = (id: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/shoppingCart/' + id,
    method: 'delete'
  });
};
