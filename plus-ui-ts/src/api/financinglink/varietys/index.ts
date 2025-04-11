import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { VarietysVO, VarietysForm, VarietysQuery } from '@/api/financinglink/varietys/types';

/**
 * 查询品种树列表
 * @param query
 * @returns {*}
 */

export const listVarietys = (query?: VarietysQuery): AxiosPromise<VarietysVO[]> => {
  return request({
    url: '/financinglink/varietys/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询品种树详细
 * @param id
 */
export const getVarietys = (id: string | number): AxiosPromise<VarietysVO> => {
  return request({
    url: '/financinglink/varietys/' + id,
    method: 'get'
  });
};

/**
 * 新增品种树
 * @param data
 */
export const addVarietys = (data: VarietysForm) => {
  return request({
    url: '/financinglink/varietys',
    method: 'post',
    data: data
  });
};

/**
 * 修改品种树
 * @param data
 */
export const updateVarietys = (data: VarietysForm) => {
  return request({
    url: '/financinglink/varietys',
    method: 'put',
    data: data
  });
};

/**
 * 删除品种树
 * @param id
 */
export const delVarietys = (id: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/varietys/' + id,
    method: 'delete'
  });
};
