package com.anhquoc.service;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;

import com.anhquoc.entity.AccountEntity;
import com.anhquoc.entity.UserEntity;

public interface IUserService {
	public List<UserEntity> findUserByEmail(String email);
	public UserEntity findUserById(Long id);
	public List<UserEntity> findUserByName(String name);
	public UserEntity getUserByEmailAndPassword(String email, String password);
//	public void getSignUpCode(String email, JavaMailSender javaMailSender);
//	public boolean resetPassword(String email, String subject, String content,JavaMailSender javaMailSender);
//	public UserEntity createUser(UserEntity user, AccountEntity account);
	public UserEntity updateUser(UserEntity user);
	public AccountEntity changePassword(AccountEntity account, String email);
	public List<UserEntity> getListUser();
	public List<UserEntity> getListUserbyType(Long type);
	public List<UserEntity> getListUserbyTypeandStatus(Long type, boolean status);
	public UserEntity statusUser(Long userid);
	public List<UserEntity> getListUserbySearch(String search);
}
