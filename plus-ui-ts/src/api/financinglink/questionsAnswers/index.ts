import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { QuestionsAnswersVO, QuestionsAnswersForm, QuestionsAnswersQuery } from '@/api/financinglink/questionsAnswers/types';

/**
 * 查询问答列表--外部接口
 * @param query
 * @returns {*}
 */

export const listQuestionsAnswers = (query?: QuestionsAnswersQuery): AxiosPromise<QuestionsAnswersVO[]> => {
  return request({
    url: '/financinglink/out/questionsAnswers/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询问答列表--内部接口
 * @param query
 * @returns {*}
 */

export const listQuestionsAnswersMy = (query?: QuestionsAnswersQuery): AxiosPromise<QuestionsAnswersVO[]> => {
  return request({
    url: '/financinglink/questionsAnswers/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询问答详细
 * @param id
 */
export const getQuestionsAnswers = (id: string | number): AxiosPromise<QuestionsAnswersVO> => {
  return request({
    url: '/financinglink/questionsAnswers/' + id,
    method: 'get'
  });
};

/**
 * 新增问答
 * @param data
 */
export const addQuestionsAnswers = (data: QuestionsAnswersForm) => {
  return request({
    url: '/financinglink/questionsAnswers',
    method: 'post',
    data: data
  });
};

/**
 * 修改问答
 * @param data
 */
export const updateQuestionsAnswers = (data: QuestionsAnswersForm) => {
  return request({
    url: '/financinglink/questionsAnswers',
    method: 'put',
    data: data
  });
};

/**
 * 删除问答
 * @param id
 */
export const delQuestionsAnswers = (id: string | number | Array<string | number>) => {
  return request({
    url: '/financinglink/questionsAnswers/' + id,
    method: 'delete'
  });
};
