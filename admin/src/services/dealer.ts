import { request } from 'umi';

/** 获取医院 */
export async function queryDealer(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.PageResult>('/api/dealer', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 添加门店 */
export async function addDealer(
  body: {
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/dealer', {
    method: 'POST',
    data: body,
    headers: {
      'Content-Type': 'application/json',
    },
    ...(options || {}),
  });
}

/** 更新门店 */
export async function modifyDearler(
  body: {
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/dealer', {
    method: 'PUT',
    data: body,
    headers: {
      'Content-Type': 'application/json',
    },
    ...(options || {}),
  });
}

/** 删除门店 */
export async function deleteDealer(
  params: {
    id: number
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/dealer/'+params.id, {
    method: 'DELETE',
    ...(options || {}),
  });
}

/** 重置密码 */
export async function restAccountPassword(
  params: {
    id: number
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/dealer/account/'+params.id+'/password', {
    method: 'PATCH',
    ...(options || {}),
  });
}