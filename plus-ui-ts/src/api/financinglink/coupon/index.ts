import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { CouponVO, CouponForm, CouponQuery } from '@/api/financinglink/coupon/types';

/**
 * 查询优惠卷列表
 * @param query
 * @returns {*}
 */

export const listCoupon = (query?: CouponQuery): AxiosPromise<CouponVO[]> => {
  return request({
    url: '/financinglink/coupon/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询优惠卷详细
 * @param userId
 */
export const getCoupon = (userId: string | number): AxiosPromise<CouponVO> => {
  return request({
    url: '/financinglink/coupon/' + userId,
    method: 'get'
  });
};

/**
 * 新增优惠卷
 * @param data
 */
export const addCoupon = (data: CouponForm) => {
  return request({
    url: '/financinglink/coupon',
    method: 'post',
    data: data
  });
};

/**
 * 修改优惠卷
 * @param data
 */
export const updateCoupon = (data: CouponForm) => {
  return request({
    url: '/financinglink/coupon',
    method: 'put',
    data: data
  });
};

/**
 * 删除优惠卷
 * @param userId
 */
export const delCoupon = (userId: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/coupon/' + userId,
    method: 'delete'
  });
};
