package com.anhquoc.service;

import com.anhquoc.entity.AccountEntity;
import com.anhquoc.entity.UserEntity;


public interface IAccountService {
	public AccountEntity createAccount(AccountEntity account, UserEntity user);
}
