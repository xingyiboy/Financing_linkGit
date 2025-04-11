import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { SpecialistVO, SpecialistForm, SpecialistQuery } from '@/api/financinglink/specialist/types';

/**
 * 查询专家详情列表--外部接口
 * @param query
 * @returns {*}
 */

export const listSpecialist = (query?: SpecialistQuery): AxiosPromise<SpecialistVO[]> => {
  return request({
    url: '/financinglink/out/specialist/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询专家详情详细
 * @param id
 */
export const getSpecialist = (id: string | number): AxiosPromise<SpecialistVO> => {
  return request({
    url: '/financinglink/specialist/' + id,
    method: 'get'
  });
};

/**
 * 新增专家详情
 * @param data
 */
export const addSpecialist = (data: SpecialistForm) => {
  return request({
    url: '/financinglink/specialist',
    method: 'post',
    data: data
  });
};

/**
 * 修改专家详情
 * @param data
 */
export const updateSpecialist = (data: SpecialistForm) => {
  return request({
    url: '/financinglink/specialist',
    method: 'put',
    data: data
  });
};

/**
 * 删除专家详情
 * @param id
 */
export const delSpecialist = (id: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/specialist/' + id,
    method: 'delete'
  });
};

/**
 * 查询是否是专家
 * @param id
 */
export const isSpecialist = (): AxiosPromise<number> => {
  return request({
    url: '/financinglink/specialist/isSpecialist',
    method: 'get'
  });
};
