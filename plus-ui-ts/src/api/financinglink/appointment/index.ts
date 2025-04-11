import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { AppointmentVO, AppointmentForm, AppointmentQuery } from '@/api/financinglink/appointment/types';

/**
 * 查询预约列表
 * @param query
 * @returns {*}
 */

export const listAppointment = (query?: AppointmentQuery): AxiosPromise<AppointmentVO[]> => {
  return request({
    url: '/financinglink/appointment/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询预约详细
 * @param id
 */
export const getAppointment = (id: string | number): AxiosPromise<AppointmentVO> => {
  return request({
    url: '/financinglink/appointment/' + id,
    method: 'get'
  });
};

/**
 * 新增预约
 * @param data
 */
export const addAppointment = (data: AppointmentForm) => {
  return request({
    url: '/financinglink/appointment',
    method: 'post',
    data: data
  });
};

/**
 * 修改预约
 * @param data
 */
export const updateAppointment = (data: AppointmentForm) => {
  return request({
    url: '/financinglink/appointment',
    method: 'put',
    data: data
  });
};

/**
 * 删除预约
 * @param id
 */
export const delAppointment = (id: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/appointment/' + id,
    method: 'delete'
  });
};
