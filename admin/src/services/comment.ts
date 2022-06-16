import { request } from 'umi';

/** 获取列表 */
export async function queryComment(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.PageResult>('/api/comment', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 获取列表 */
export async function queryReplyComment(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.PageResult>('/api/comment/reply', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

