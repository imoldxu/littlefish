// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 获取规则列表 GET /api/rule */
export async function queryArticle(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.PageResult>('/api/article', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

export async function getArticle(
  params: {
    // query
    /** 处方id */
    id?: number;
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/article/'+params.id, {
    method: 'GET',
    // params: {
    //   ...params,
    // },
    ...(options || {}),
  });
}

/** 新建规则 PUT /api/rule */
export async function modifyArticle(data:any, options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/article', {
    method: 'PUT',
    data: data,
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addArticle(data: any, options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/article', {
    method: 'POST',
    data: data,
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function delArticle(params:{
  id: number
  }, options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/article/'+params.id, {
    method: 'DELETE',
    ...(options || {}),
  });
}
