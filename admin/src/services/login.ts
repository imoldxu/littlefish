// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 登出接口 */
export async function logout(options?: { [key: string]: any }) {
  return request<any>('/api/staff/session', {
    method: 'DELETE',
    ...(options || {}),
  });
}

/** 登录接口 POST /api/login/account */
export async function login(body: API.LoginParams, options?: { [key: string]: any }) {
  return request<any>('/api/staff/session', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取当前的用户 GET /api/staff/session */
export async function currentUser(options?: { [key: string]: any }) {
  return request<API.CurrentUser>('/api/staff/session', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 修改密码 */
export async function modifyPassword(body: {oldPassword: string, newPassword: string}, options?: { [key: string]: any }) {
  return request<any>('/api/staff/password', {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}