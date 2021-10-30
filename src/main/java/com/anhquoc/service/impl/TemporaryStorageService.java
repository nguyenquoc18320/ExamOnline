package com.anhquoc.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.TemporaryStorage;
import com.anhquoc.repository.TemporaryStorageRepository;
import com.anhquoc.service.ITemporaryStorageService;

@Service
public class TemporaryStorageService implements ITemporaryStorageService {
	
	@Autowired
	TemporaryStorageRepository tempStorageRepository;
	
	@Override
	public void getSignUpCode(String email, JavaMailSender javaMailSender) {
		Random rand = new Random();
		String code = "";
		
		//code includes 6 digits
		while(code.length()!=6) {
			code += String.valueOf(rand.nextInt(10));
		}
		
		System.out.println("Code: "+code);
		
		//save to db
		TemporaryStorage tempEntity = new TemporaryStorage(email, code);
		tempStorageRepository.save(tempEntity);
		
		//send code to email
		Utils.sendEmail(email, "Online Test - Code to confirm email", "Your code:\n"+ code, javaMailSender);
	}

	@Override
	public boolean confirmCode(String email, String code) {
		TemporaryStorage temp = tempStorageRepository.findOneBySubject(email);
		if(temp != null && temp.getCode().equals(code)) {
			return true;
		}
		return false;
	}	
}
