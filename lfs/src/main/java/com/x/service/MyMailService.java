package com.x.service;

/**
 * 邮件服务
 * @author 老徐
 *
 */
public interface MyMailService {

	void sendSimpleMail(String to, String subject, String content);

	void sendHtmlMail(String to, String subject, String content);

	void sendAttachmentsMail(String to, String subject, String content, String filePath);

}
