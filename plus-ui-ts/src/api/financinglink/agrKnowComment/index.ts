import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { AgrKnowCommentVO, AgrKnowCommentForm, AgrKnowCommentQuery } from '@/api/financinglink/agrKnowComment/types';

/**
 * 查询农业知识评价列表--外部接口
 * @param query
 * @returns {*}
 */

export const listAgrKnowComment = (query?: AgrKnowCommentQuery): AxiosPromise<AgrKnowCommentVO[]> => {
  return request({
    url: '/financinglink/out/agrKnowComment/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询农业知识评价详细
 * @param userId
 */
export const getAgrKnowComment = (userId: string | number): AxiosPromise<AgrKnowCommentVO> => {
  return request({
    url: '/financinglink/agrKnowComment/' + userId,
    method: 'get'
  });
};

/**
 * 新增农业知识评价
 * @param data
 */
export const addAgrKnowComment = (data: AgrKnowCommentForm) => {
  return request({
    url: '/financinglink/agrKnowComment',
    method: 'post',
    data: data
  });
};

/**
 * 修改农业知识评价
 * @param data
 */
export const updateAgrKnowComment = (data: AgrKnowCommentForm) => {
  return request({
    url: '/financinglink/agrKnowComment',
    method: 'put',
    data: data
  });
};

/**
 * 删除农业知识评价
 * @param userId
 */
export const delAgrKnowComment = (userId: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/agrKnowComment/' + userId,
    method: 'delete'
  });
};
