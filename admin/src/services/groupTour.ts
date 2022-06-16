// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

export async function queryGroupTour(params: any, options?: { [key: string]: any }) {
  return request<any>('/api/grouptour', {
    method: 'GET',
    params: params,
    ...(options || {}),
  });
}

export async function addGroupTour(body: any, options?: { [key: string]: any }) {
  return request<any>('/api/grouptour', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

export async function modifyGroupTour(body: any, options?: { [key: string]: any }) {
  return request<any>('/api/grouptour', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}