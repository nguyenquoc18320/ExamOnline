package com.anhquoc.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class Utils {
	public static void sendEmail(String email, String subject, String content, JavaMailSender javaMailSender) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);

		msg.setSubject(subject);
		msg.setText(content);

		javaMailSender.send(msg);
	}
	
}
