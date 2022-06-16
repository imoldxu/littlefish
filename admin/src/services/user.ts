import { request } from 'umi';

/** 获取 */
export async function queryUser(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.PageResult>('/api/user', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

export async function getUserDetail(
  params: {
    // query
    /** id */
    id?: number;
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/user/'+params.id, {
    method: 'GET',
    ...(options || {}),
  });
}

export async function getUserTrend(
  params: {
    // query
    timeDimension: number; //时间维度 1年，2月，3天，4分
    lastTime: string; //yyyy-MM-dd HH:mm:ss
    intervalNum: number; //间隔区间
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/user/new/trend', {
    method: 'GET',
    params,
    ...(options || {}),
  });
}

export async function getUserRatio(
  params: {},
  options?: { [key: string]: any },
) {
  return request<any>('/api/user/channel/ratio', {
    method: 'GET',
    params,
    ...(options || {}),
  });
}