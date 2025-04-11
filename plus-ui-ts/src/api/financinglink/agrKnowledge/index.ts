import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { AgrKnowledgeVO, AgrKnowledgeForm, AgrKnowledgeQuery } from '@/api/financinglink/agrKnowledge/types';

/**
 * 查询农业知识列表
 * @param query
 * @returns {*}
 */

export const listAgrKnowledge = (query?: AgrKnowledgeQuery): AxiosPromise<AgrKnowledgeVO[]> => {
  return request({
    url: '/financinglink/agrKnowledge/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询农业知识列表--外部接口
 * @param query
 * @returns {*}
 */

export const listAgrKnowledgeOut = (query?: AgrKnowledgeQuery): AxiosPromise<AgrKnowledgeVO[]> => {
  return request({
    url: '/financinglink/out/agrKnowledge/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询农业知识详细--外部接口
 * @param id
 */
export const getAgrKnowledge = (id: string | number): AxiosPromise<AgrKnowledgeVO> => {
  return request({
    url: '/financinglink/out/agrKnowledge/' + id,
    method: 'get'
  });
};

/**
 * 新增农业知识
 * @param data
 */
export const addAgrKnowledge = (data: AgrKnowledgeForm) => {
  return request({
    url: '/financinglink/agrKnowledge',
    method: 'post',
    data: data
  });
};

/**
 * 修改农业知识
 * @param data
 */
export const updateAgrKnowledge = (data: AgrKnowledgeForm) => {
  return request({
    url: '/financinglink/agrKnowledge',
    method: 'put',
    data: data
  });
};

/**
 * 删除农业知识
 * @param id
 */
export const delAgrKnowledge = (id: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/agrKnowledge/' + id,
    method: 'delete'
  });
};
