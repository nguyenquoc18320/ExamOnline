package com.anhquoc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.UserEntity;
import com.anhquoc.repository.UserRepository;
import com.anhquoc.service.IUserService;

@Service
public class UserService implements IUserService{
	@Autowired
	private UserRepository userRepository;
	

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
	public UserEntity createUser(UserEntity user) {
		user = userRepository.save(user);
		return user;
	}
	
	
	
}
