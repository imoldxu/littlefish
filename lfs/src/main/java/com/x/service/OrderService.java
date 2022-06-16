package com.x.service;

import java.util.Date;
import java.util.List;

import com.x.data.bo.CommitOrderBo;
import com.x.data.po.Order;
import com.x.data.pojo.enums.PayMode;
import com.x.data.vo.OrderPreviewVo;
import com.x.data.vo.UserVo;

/**
 * ��������
 * 
 * @author ����
 *
 */
public interface OrderService {

	/**
	 * Ԥ�����������㶩���Ľ��Ƿ�����Ż�ȯ����ݽ��ȵ�
	 * 
	 * @param user
	 * @param commitOrderBo
	 * @return
	 */
	public OrderPreviewVo preview(UserVo user, CommitOrderBo commitOrderBo);

	/**
	 * ��������
	 * 
	 * @param user
	 * @param commitOrderBo
	 * @return
	 */
	public Order newOrder(String uid, CommitOrderBo newOrderBo);
	
	public List<Order> getMyOrders(String uid);
	
	public Order getMyOrder(String uid, String orderId);

	public Object getCharge(UserVo user, String orderid, String payWay, String ip);

	public void expire(String no);

	public void payOver(String orderNo, Integer amount, PayMode wxpay, String transactionId, Date successDate);
}
