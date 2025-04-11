import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { AddressVO, AddressForm, AddressQuery } from '@/api/financinglink/address/types';

/**
 * 查询用户地址列表
 * @param query
 * @returns {*}
 */

export const listAddress = (query?: AddressQuery): AxiosPromise<AddressVO[]> => {
  return request({
    url: '/financinglink/address/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询用户地址列表--外部接口
 * @param query
 * @returns {*}
 */

export const listAddressOut = (query?: AddressQuery): AxiosPromise<AddressVO[]> => {
  return request({
    url: '/financinglink/out/address/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询用户地址详细
 * @param id
 */
export const getAddress = (id: string | number): AxiosPromise<AddressVO> => {
  return request({
    url: '/financinglink/address/' + id,
    method: 'get'
  });
};

/**
 * 新增用户地址
 * @param data
 */
export const addAddress = (data: AddressForm) => {
  return request({
    url: '/financinglink/address',
    method: 'post',
    data: data
  });
};

/**
 * 修改用户地址
 * @param data
 */
export const updateAddress = (data: AddressForm) => {
  return request({
    url: '/financinglink/address',
    method: 'put',
    data: data
  });
};

/**
 * 删除用户地址
 * @param id
 */
export const delAddress = (id: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/address/' + id,
    method: 'delete'
  });
};
