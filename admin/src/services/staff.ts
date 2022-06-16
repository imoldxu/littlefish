// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 查询员工列表 */
export async function queryStaff(params:{phone: string, name: string, role: string, state: number}, options?: { [key: string]: any }) {
  return request<any>('/api/staff', {
    method: 'GET',
    params: params,
    ...(options || {}),
  });
}

/** 注册接口 */
export async function register(body: { phone: string, name:string, role:string }, options?: { [key: string]: any }) {
  return request<any>('/api/staff', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

export async function enableStaff(body: { id: number }, options?: { [key: string]: any }) {
  return request<any>('/api/staff/'+body.id+ '/state/enable', {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    ...(options || {}),
  });
}

export async function disableStaff(body: { id: number }, options?: { [key: string]: any }) {
  return request<any>('/api/staff/'+body.id+ '/state/disable', {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    ...(options || {}),
  });
}

/** 重置密码接口 */
export async function resetPassword(body: {id: number}, options?: { [key: string]: any }) {
  return request<any>('/api/staff/'+body.id+'/password', {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    // data: body,
    ...(options || {}),
  });
}