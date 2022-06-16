// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 获取广告 */
export async function getBanner(params?: any, options?: { [key: string]: any }) {
  return request<API.CurrentUser>('/api/banner', {
    method: 'GET',
    params: params,
    ...(options || {}),
  });
}

/** 发布广告 */
export async function addBanner(data: any, options?: { [key: string]: any }) {
    return request<Record<string, any>>('/api/banner', {
      method: 'POST',
      data: data,
      ...(options || {}),
    });
  }

  /** 修改广告 */
export async function modifyBanner(data: any, options?: { [key: string]: any }) {
    return request<Record<string, any>>('/api/banner', {
      method: 'PUT',
      data: data,
      ...(options || {}),
    });
  }

