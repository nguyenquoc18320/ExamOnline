package com.anhquoc.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface ITemporaryStorageService {
	public void getSignUpCode(String email, JavaMailSender javaMailSender);
	public boolean confirmCode(String email, String code);
}
