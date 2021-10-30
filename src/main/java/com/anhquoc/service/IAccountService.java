package com.anhquoc.service;

import com.anhquoc.entity.AccountEntity;
import com.anhquoc.entity.UserEntity;


public interface IAccountService {
	public AccountEntity createAccount(AccountEntity account, UserEntity user);
	public AccountEntity findAccountByUserid(Long userid);
	public AccountEntity createAccount(AccountEntity account, String code);
}
