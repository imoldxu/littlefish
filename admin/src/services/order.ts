// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 获取病人的订单 */
export async function queryPatientOrder(
    params: {
        // query
        /** 当前的页码 */
        current?: number;
        /** 页面的容量 */
        pageSize?: number;
    },
    options?: { [key: string]: any },
) {
    return request<API.PageResult>('/api/order/queryPatientOrder', {
        method: 'GET',
        params: {
            ...params,
        },
        ...(options || {}),
    });
}

//获取所有的订单
export async function queryOrder(
    params: {
        // query
        /** 当前的页码 */
        current?: number;
        /** 页面的容量 */
        pageSize?: number;
    },
    options?: { [key: string]: any },
) {
    return request<API.PageResult>('/api/order', {
        method: 'GET',
        params: {
            ...params,
        },
        ...(options || {}),
    });
}

export async function getOrderDetail(
    params: {
        id?: number;
    },
    options?: { [key: string]: any },
) {
    return request<any>('/api/order/'+params.id, {
        method: 'GET',
        params: {
            ...params,
        },
        ...(options || {}),
    });
}

/** 出货 */
export async function deliver(data: { orderno: string }, options?: { [key: string]: any }) {
    return request<any>('/api/order/deliver', {
        method: 'PUT',
        data: data,
        ...(options || {}),
    });
}

/** 退货 */
export async function refundDrug(data: { orderid: number, records: any }, options?: { [key: string]: any }) {
    return request<any>('/api/order/refundDrug', {
        method: 'POST',
        data: data,
        ...(options || {}),
    });
}

// /** 现金支付完成 */
// export async function cashOver(data: { orderno: string }, options?: { [key: string]: any }) {
//     return request<any>('/api/order/cashOver', {
//         method: 'PUT',
//         data: data,
//         ...(options || {}),
//     });
// }

// /** 银行卡支付完成 */
// export async function bankcardOver(data: { orderno: string }, options?: { [key: string]: any }) {
//     return request<any>('/api/order/bankcardOver', {
//         method: 'PUT',
//         data: data,
//         ...(options || {}),
//     });
// }

// /** 线下退款 */
// export async function offlineRefund(data: { orderno: string }, options?: { [key: string]: any }) {
//     return request<any>('/api/order/offlineRefund', {
//         method: 'PUT',
//         data: data,
//         ...(options || {}),
//     });
// }

// /** 医保支付完成 */
// export async function yibaoOver(data: { orderno: string }, options?: { [key: string]: any }) {
//     return request<any>('/api/order/yibaoOver', {
//         method: 'PUT',
//         data: data,
//         ...(options || {}),
//     });
// }

// /** 医保支付完成 */
// export async function yidiYibaoOver(data: { orderno: string }, options?: { [key: string]: any }) {
//     return request<any>('/api/order/yidiYibaoOver', {
//         method: 'PUT',
//         data: data,
//         ...(options || {}),
//     });
// }

export async function getTradeTrend(
    params: {
      // query
      timeDimension: number; //时间维度 1年，2月，3天，4分
      lastTime: string; //yyyy-MM-dd HH:mm:ss
      intervalNum: number; //间隔区间
    },
    options?: { [key: string]: any },
  ) {
    return request<any>('/api/order/trade/trend', {
      method: 'GET',
      params,
      ...(options || {}),
    });
  }