import { request } from 'umi';

/** 获取医院 */
export async function queryCouponTemplate(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.PageResult>('/api/coupon/template', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

export async function getCouponTemplateDetail(
  params: {
    // query
    /** 处方id */
    id?: number;
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/coupon/template/'+params.id, {
    method: 'GET',
    // params: {
    //   ...params,
    // },
    ...(options || {}),
  });
}

/** 添加门店 */
export async function addCouponTemplate(
  body: {
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/coupon/template', {
    method: 'POST',
    data: body,
    headers: {
      'Content-Type': 'application/json',
    },
    ...(options || {}),
  });
}

/** 更新门店 */
export async function modifyCouponTemplate(
  body: {
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/coupon/template', {
    method: 'PUT',
    data: body,
    headers: {
      'Content-Type': 'application/json',
    },
    ...(options || {}),
  });
}

/** 删除门店 */
export async function deleteCouponTemplate(
  params: {
    id: number
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/coupon/template/'+params.id, {
    method: 'DELETE',
    ...(options || {}),
  });
}
