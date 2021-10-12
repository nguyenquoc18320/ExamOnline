package com.anhquoc.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.anhquoc.api.UserAPI;
import com.anhquoc.entity.AccountEntity;
import com.anhquoc.entity.TemporaryStorage;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.repository.AccountRepository;
import com.anhquoc.repository.TemporaryStorageRepository;
import com.anhquoc.repository.UserRepository;
import com.anhquoc.service.IUserService;

@Service
public class UserService implements IUserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TemporaryStorageRepository tempStorageRepository;
	
	@Override
	public List<UserEntity> findUserByEmail(String email) {
		List<UserEntity> user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public UserEntity findUserById(Long id) {
		UserEntity user = userRepository.findOneById(id);
		return user;
	}

	@Override
	public List<UserEntity> findUserByName(String name) {
		List<UserEntity> users = userRepository.findByName(name);
		return users;
	}

	@Override
	public UserEntity getUserByEmailAndPassword(String email, String password) {
		UserEntity user = userRepository.findOneByEmail(email);
		AccountEntity account = accountRepository.findOneByUser(user);

		if (account == null || !account.getPassword().equals(password))
			return null;
		return user;
	}

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
	

//	@Override
//	public boolean resetPassword(String email, String subject, String content, JavaMailSender javaMailSender) {
////		try {
////			Utils.sendEmail(email, subject, content, javaMailSender);
////		} catch (Exception e) {
////			return false;
////		}
////		return true;
//	}

//	@Override
//	public UserEntity createUser(UserEntity user, AccountEntity account) {
//		if(findUserByEmail(user.getEmail()).size()>0) {
//			return null;
//		}
//		
//		user.setId(null);
//		account.setId(null);
//		
//		user = userRepository.save(user);
//		account.setUser(user);
//		account = accountRepository.save(account);
////		System.out.println(account.getUser().getId());
//		return user;
//	}

}
