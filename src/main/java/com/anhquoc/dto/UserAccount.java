package com.anhquoc.dto;

import com.anhquoc.entity.AccountEntity;
import com.anhquoc.entity.UserEntity;

public class UserAccount {
	private UserEntity user;
	private AccountEntity account;
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public AccountEntity getAccount() {
		return account;
	}
	public void setAccount(AccountEntity account) {
		this.account = account;
	}
	
	
}
