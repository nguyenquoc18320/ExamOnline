package com.anhquoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.AccountEntity;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.repository.AccountRepository;
import com.anhquoc.service.IAccountService;

@Service
public class AccountService implements IAccountService{
	@Autowired
	AccountRepository accountRepository;

	@Override
	public AccountEntity createAccount(AccountEntity account, UserEntity user) {
		
		return null;
	}


}
