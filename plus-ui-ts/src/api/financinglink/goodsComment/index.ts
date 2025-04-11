import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { GoodsCommentVO, GoodsCommentForm, GoodsCommentQuery } from '@/api/financinglink/goodsComment/types';

/**
 * 查询商品评价列表
 * @param query
 * @returns {*}
 */

export const listGoodsComment = (query?: GoodsCommentQuery): AxiosPromise<GoodsCommentVO[]> => {
  return request({
    url: '/financinglink/goodsComment/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询商品评价列表 外部接口
 * @param query
 * @returns {*}
 */

export const listGoodsCommentOut = (query?: GoodsCommentQuery): AxiosPromise<GoodsCommentVO[]> => {
  return request({
    url: '/financinglink/out/goodsComment/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询商品评价详细
 * @param userId
 */
export const getGoodsComment = (userId: string | number): AxiosPromise<GoodsCommentVO> => {
  return request({
    url: '/financinglink/goodsComment/' + userId,
    method: 'get'
  });
};

/**
 * 新增商品评价
 * @param data
 */
export const addGoodsComment = (data: GoodsCommentForm) => {
  return request({
    url: '/financinglink/goodsComment',
    method: 'post',
    data: data
  });
};

/**
 * 修改商品评价
 * @param data
 */
export const updateGoodsComment = (data: GoodsCommentForm) => {
  return request({
    url: '/financinglink/goodsComment',
    method: 'put',
    data: data
  });
};

/**
 * 删除商品评价
 * @param userId
 */
export const delGoodsComment = (userId: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/goodsComment/' + userId,
    method: 'delete'
  });
};
