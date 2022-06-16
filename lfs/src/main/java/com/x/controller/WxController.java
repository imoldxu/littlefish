package com.x.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.x.constant.ErrorCode;
import com.x.data.bo.WxConfigBo;
import com.x.data.pojo.enums.PayMode;
import com.x.data.pojo.wxpay.WxNotice;
import com.x.data.pojo.wxpay.WxResponse;
import com.x.data.pojo.wxpay.WxTransaction;
import com.x.data.vo.WxConfig;
import com.x.exception.HandleException;
import com.x.service.OrderService;
import com.x.service.WxOfficialAccountService;
import com.x.service.WxPayService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/wx")
@Api("微信接口")
public class WxController {

	@Autowired
	WxOfficialAccountService wxOfficialAccountService;
	@Autowired
	WxPayService wxPayService;
	@Autowired
	OrderService orderService;

	/**
	 * 公众号
	 * 
	 * @param wxConfigBo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = "/jssdk_config", method = RequestMethod.GET)
	@ApiOperation(value = "获取JSAPI的配置", notes = "获取JSAPI的配置")
	public WxConfig getJSAPIConfig(@ApiParam(name = "wxConfigBo", value = "页面的url") @Valid WxConfigBo wxConfigBo,
			HttpServletRequest request, HttpServletResponse response) {
		WxConfig wxConfig;
		try {
			wxConfig = wxOfficialAccountService.sign(wxConfigBo.getUrl());
		} catch (URISyntaxException | IOException e) {
			throw new HandleException(ErrorCode.WX_NET_ERROR, "微信服务请求错误");
		}
		return wxConfig;
	}

	/**
	 * 微信支付通知接口，必须是https，设置nginx代理的话，需要在回调中加入/api
	 * 
	 * @param notice
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@RequestMapping(path = "/pay/notice", method = RequestMethod.POST)
	@ApiOperation(value = "微信支付通知", notes = "微信支付通知")
	public WxResponse payNotify(@ApiParam(name = "wxConfigBo", value = "页面的url") @Valid @RequestBody String notice,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException, GeneralSecurityException, IOException {

		// 签名值
		String WechatpaySignature = request.getHeader("Wechatpay-Signature");
		// 需要比较证书的序列号，不一致需要获取新的
		String WechatpaySerial = request.getHeader("Wechatpay-Serial");
		String WechatpayTimestamp = request.getHeader("Wechatpay-Timestamp");
		String WechatpayNonce = request.getHeader("Wechatpay-Nonce");
		if (StringUtils.isBlank(WechatpaySignature)) {
			response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			WxResponse faile = new WxResponse();
			faile.setCode("FAILE");
			return faile;
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(WechatpayTimestamp);
		buffer.append("\n");
		buffer.append(WechatpayNonce);
		buffer.append("\n");
		buffer.append(notice);
		buffer.append("\n");
		String content = buffer.toString();

		if (!wxPayService.verify(content, WechatpaySignature)) {
			response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			WxResponse faile = new WxResponse();
			faile.setCode("FAILE");
			return faile;
		}

		WxNotice wxNotice = JSON.parseObject(notice, WxNotice.class);
		WxTransaction transaction = wxPayService.decrypt(wxNotice.getResource());
		String orderNo = transaction.getOut_trade_no();
		transaction.getAmount();
		String transactionId = transaction.getTransaction_id(); // 流水号

		String tradeState = transaction.getTrade_state();
		String successTime = transaction.getSuccess_time();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date successDate = format.parse(successTime);

		// TODO: 支付金额和订单金额不一致会如何？
		Integer amount = transaction.getAmount().getPayer_total();

		if (tradeState.equalsIgnoreCase("SUCCESS")) {
			orderService.payOver(orderNo, amount, PayMode.WXPAY, transactionId, successDate);
		} else if (tradeState.equalsIgnoreCase("REFUND")) {
//        	orderService.refundOver(orderNo, amount, PayMode.WXPAY, transactionId, successDate);
		} else if (tradeState.equalsIgnoreCase("REVOKED")) {
			// FIXME: 付款码支付
		}

		WxResponse success = new WxResponse();
		success.setCode("SUCCESS");
		return success;
	}

}
