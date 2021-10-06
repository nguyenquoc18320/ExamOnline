package com.anhquoc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anhquoc.entity.AccountEntity;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.service.impl.AccountService;
import com.anhquoc.service.impl.UserService;

@CrossOrigin
@RestController
public class UserAPI {
	@Autowired
	UserService userService;
	
	@Autowired
	AccountService accountService;
	
	@PostMapping(value = "/user")
	public UserEntity createUser(@RequestBody UserEntity userEntity, @RequestBody AccountEntity accountEntity) {
//		userEntity = userService.createUser(userEntity);
//		AccountEntity account = accountService.createAccount(accountEntity);
//		
		return null;
	}
	
	@GetMapping(value = "/user-email/{email}")
	public List<UserEntity> getUserByEmail(@PathVariable("email") String email) {
		List<UserEntity>  user = userService.findUserByEmail(email);
		return user;
	}
}
