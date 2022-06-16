import { request } from 'umi';

/** 获取后端签名 */
export async function getSign(params?: any, options?: { [key: string]: any }) {
  return request<any>('/api/aliyun/oss/signature', {
    method: 'GET',
    params: params,
    ...(options || {}),
  });
}

/** 获取后端STS授权 */
export async function getSTS(params?: any, options?: { [key: string]: any }) {
  return request<any>('/api/aliyun/sts', {
    method: 'GET',
    params: params,
    ...(options || {}),
  });
}
