import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { VarietysVO, VarietysForm, VarietysQuery } from '@/api/financinglink/varietys/types';
import { GoodsVO, GoodsForm, GoodsQuery } from '@/api/financinglink/goods/types';
import { SpecificationVO, SpecificationForm, SpecificationQuery } from '@/api/financinglink/specification/types';

/**
 * 查询品种树列表
 * @param query
 * @returns {*}
 */

export const listVarietys = (query?: VarietysQuery): AxiosPromise<VarietysVO[]> => {
  return request({
    url: '/financinglink/out/varietys/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询商品和需求列表
 * @param query
 * @returns {*}
 */

export const listGoods = (query?: GoodsQuery): AxiosPromise<GoodsVO[]> => {
  return request({
    url: '/financinglink/out/goods/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询商品和需求详细
 * @param id
 */
export const getGoods = (id: string | number): AxiosPromise<GoodsVO> => {
  return request({
    url: '/financinglink/out/' + id,
    method: 'get'
  });
};

/**
 * 查询商品规格详细
 * @param goodsId
 */
export const getFlSpecificationList = (goodsId: string | number): AxiosPromise<SpecificationVO> => {
  return request({
    url: '/financinglink/out/flSpecification/' + goodsId,
    method: 'get'
  });
};
