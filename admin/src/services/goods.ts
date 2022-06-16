// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 查询药品 */
export async function queryGoods(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.PageResult>('/api/goods', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

export async function getGoodsDetail(
  params: {
    // query
    /** 处方id */
    id?: number;
  },
  options?: { [key: string]: any },
) {
  return request<any>('/api/goods/'+params.id, {
    method: 'GET',
    // params: {
    //   ...params,
    // },
    ...(options || {}),
  });
}

/** 添加药品 */
export async function addGoods(data:any, options?: { [key: string]: any }) {
  return request<any>('/api/goods', {
    method: 'POST',
    data: data,
    ...(options || {}),
  });
}

/** 修改药品 */
export async function modifyGoods(data:any, options?: { [key: string]: any }) {
    return request<any>('/api/goods', {
      method: 'PUT',
      data: data,
      ...(options || {}),
    });
  }

/** 下架药品 */
export async function downGoods(data:{id:number}, options?: { [key: string]: any }) {
  return request<any>('/api/goods/'+data.id+'/state/disable', {
    method: 'PATCH',
    //data: data,
    ...(options || {}),
  });
}

/** 上架药品 */
export async function upGoods(data:{id:number}, options?: { [key: string]: any }) {
    return request<any>('/api/goods/'+data.id+'/state/enable', {
      method: 'PATCH',
      //data: data,
      ...(options || {}),
    });
  }
  

/** 获取Sku */
export async function getSkus(params:{id:number}, options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/goods/'+params.id+'/sku', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取Sku */
export async function addSku(data: any, options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/goods/sku', {
    method: 'POST',
    data,
    ...(options || {}),
  });
}

/** 获取Sku */
export async function modifySku(data: any, options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/goods/sku', {
    method: 'PUT',
    data,
    ...(options || {}),
  });
}


/** 删除Sku */
export async function delSku(data: any, options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/goods/sku/'+data.id, {
    method: 'DELETE',
    ...(options || {}),
  });
}

/** 修改库存 */
export async function modifyStock(data:{id:number, stock: number}, options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/goods/'+data.id+'/stock', {
    method: 'PATCH',
    data: {stock: data.stock},
    ...(options || {}),
  });
}
