// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

export async function queryProduct(params: any, options?: { [key: string]: any }) {
  return request<any>('/api/product', {
    method: 'GET',
    params: params,
    ...(options || {}),
  });
}

export async function getProduct(params: any, options?: { [key: string]: any }) {
  return request<any>('/api/product/'+params.id, {
    method: 'GET',
    ...(options || {}),
  });
}

export async function addProduct(body: any, options?: { [key: string]: any }) {
  return request<any>('/api/product', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

export async function modifyProduct(body: any, options?: { [key: string]: any }) {
  return request<any>('/api/product', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

export async function changeProductState(params: any, options?: { [key: string]: any }) {
  return request<any>('/api/product/'+params.id+"/state", {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    params: params,
    ...(options || {}),
  });
}


export async function addSku(body: any, options?: { [key: string]: any }) {
  return request<any>('/api/product/sku', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

export async function modifySku(body: any, options?: { [key: string]: any }) {
  return request<any>('/api/product/sku', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

export async function querySku(params: any, options?: { [key: string]: any }) {
  return request<any>('/api/product/sku', {
    method: 'GET',
    params: params,
    ...(options || {}),
  });
}