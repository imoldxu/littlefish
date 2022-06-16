// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 获取售药记录 */
export async function querySalesRecord(
  params: {
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.PageResult>('/api/sales-record', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

export async function statistic(
    params: {
      /** 当前的页码 */
      current?: number;
      /** 页面的容量 */
      pageSize?: number;
    },
    options?: { [key: string]: any },
  ) {
    return request<API.PageResult>('/api/sales-record/statistic', {
      method: 'GET',
      params: {
        ...params,
      },
      ...(options || {}),
    });
  }

export async function downloadStatistics(
  params: any,
  options?: { [key: string]: any },
) {
  return request<any>('/api/sales-record/statistic/excel', {
    method: 'GET',
    params: {
      ...params,
    },
    responseType: 'blob',
    ...(options || {}),
  });
}
