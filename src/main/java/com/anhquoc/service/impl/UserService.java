package com.anhquoc.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
	public UserEntity updateUser(UserEntity user) {
		//account.setId(null);
		AccountEntity account = accountRepository.findOneByUser(user);
		if(userRepository.findByEmail(user.getEmail()).size()>1)
			return null;
		
		//to update
//		user.setName(user.getName());
//		user.setGender(user.getGender());
//		user.setDateOfBirth(user.getDateOfBirth());
//		if(user.getImage() != null) {
//			user.setImage(user.getImage());
//		}
		user = userRepository.save(user);
//		UserEntity usercurrent = userRepository.findOneById(user.getId());
		return user;
	}
	
	@Override
	public AccountEntity changePassword(AccountEntity account, String email) {
		
		UserEntity user = userRepository.findOneByEmail(email);
		AccountEntity account2 = accountRepository.findOneByUser(user);
		account.setId(account2.getId());
		if (account2.getId() != null && account.getStatus().equals(account2.getPassword()))
			account = accountRepository.save(account);
		else 
			return null;
		return account;
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
