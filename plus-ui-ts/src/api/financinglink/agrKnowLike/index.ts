import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { AgrKnowLikeVO, AgrKnowLikeForm, AgrKnowLikeQuery } from '@/api/financinglink/agrKnowLike/types';

/**
 * 查询农业知识点赞列表
 * @param query
 * @returns {*}
 */

export const listAgrKnowLike = (query?: AgrKnowLikeQuery): AxiosPromise<AgrKnowLikeVO[]> => {
  return request({
    url: '/financinglink/agrKnowLike/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询农业知识点赞详细
 * @param userId
 */
export const getAgrKnowLike = (userId: string | number): AxiosPromise<AgrKnowLikeVO> => {
  return request({
    url: '/financinglink/agrKnowLike/' + userId,
    method: 'get'
  });
};

/**
 * 新增农业知识点赞
 * @param data
 */
export const addAgrKnowLike = (data: AgrKnowLikeForm) => {
  return request({
    url: '/financinglink/agrKnowLike',
    method: 'post',
    data: data
  });
};

/**
 * 修改农业知识点赞
 * @param data
 */
export const updateAgrKnowLike = (data: AgrKnowLikeForm) => {
  return request({
    url: '/financinglink/agrKnowLike',
    method: 'put',
    data: data
  });
};

/**
 * 删除农业知识点赞
 * @param userId
 */
export const delAgrKnowLike = (userId: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/agrKnowLike/' + userId,
    method: 'delete'
  });
};
