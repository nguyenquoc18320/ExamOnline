package com.anhquoc.service;

import java.util.List;

import com.anhquoc.entity.UserEntity;

public interface IUserService {
	public List<UserEntity> findUserByEmail(String email);
	public UserEntity findUserById(Long id);
	public List<UserEntity> findUserByName(String name);
	public UserEntity createUser(UserEntity user);
}
