package com.anhquoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.AccountEntity;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.repository.AccountRepository;
import com.anhquoc.repository.UserRepository;
import com.anhquoc.service.IAccountService;

@Service
public class AccountService implements IAccountService{
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	UserRepository userRepository;
	
	@Override
	public AccountEntity createAccount(AccountEntity account, UserEntity user) {
		
		return null;
	}

	@Override
	public AccountEntity findAccountByUserid(Long userid) {
		UserEntity user = new UserEntity();
		user.setId(userid);
		AccountEntity accountEntity = accountRepository.findOneByUser(user); 
		return accountEntity;
	}

	/*
	 * Create new user 
	 */
	@Override
	public AccountEntity createAccount(AccountEntity account) {
		//to create a new account
		account.setId(null);
		UserEntity user = account.getUser();
		
		if(userRepository.findByEmail(user.getEmail()).size()>0)
			return null;
		
		//to save new record
		user.setId(null);
		user = userRepository.save(user);
		
		account.setUser(user);
		account = accountRepository.save(account);
		return account;
	}
	
	


}
