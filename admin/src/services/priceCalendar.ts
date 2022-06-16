// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 获取规则列表 GET /api/rule */
export async function queryPriceCalendar(
  params: {
    // query
    /** 当前的页码 */
    currentYear?: number;
    currentMonth?: number;
    /** 页面的容量 */
  },
  options?: { [key: string]: any },
) {
  return request<API.PageResult>('/api/product/sku/price-calendar', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

// /** 批量设置 */
export async function setPriceCalendar(body: any, options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/product/sku/price-calendar', {
    method: 'POST',
    data: body,
    headers: {
      'Content-Type': 'application/json',
    },
    ...(options || {}),
  });
}

