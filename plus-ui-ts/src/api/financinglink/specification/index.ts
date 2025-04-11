import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { SpecificationVO, SpecificationForm, SpecificationQuery } from '@/api/financinglink/specification/types';

/**
 * 查询规格库存列表
 * @param query
 * @returns {*}
 */

export const listSpecification = (query?: SpecificationQuery): AxiosPromise<SpecificationVO[]> => {
  return request({
    url: '/financinglink/specification/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询规格库存详细
 * @param id
 */
export const getSpecification = (id: string | number): AxiosPromise<SpecificationVO> => {
  return request({
    url: '/financinglink/specification/' + id,
    method: 'get'
  });
};

/**
 * 新增规格库存
 * @param data
 */
export const addSpecification = (data: SpecificationForm) => {
  return request({
    url: '/financinglink/specification',
    method: 'post',
    data: data
  });
};

/**
 * 修改规格库存
 * @param data
 */
export const updateSpecification = (data: SpecificationForm) => {
  return request({
    url: '/financinglink/specification',
    method: 'put',
    data: data
  });
};

/**
 * 删除规格库存
 * @param id
 */
export const delSpecification = (id: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/specification/' + id,
    method: 'delete'
  });
};
