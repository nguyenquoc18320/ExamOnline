package com.anhquoc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anhquoc.entity.AccountEntity;
import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.TestEntity;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.service.impl.AccountService;
import com.anhquoc.service.impl.TemporaryStorageService;
import com.anhquoc.service.impl.UserService;

@CrossOrigin
@RestController
public class UserAPI {
	@Autowired
	UserService userService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired 
	TemporaryStorageService tempStorageService;
	
//	@PostMapping(value = "/user")
//	public UserEntity createUser(@RequestBody UserAccount userAccount) {
//		UserEntity userEntity = userAccount.getUser();
//		AccountEntity accountEntity = userAccount.getAccount();
//		userEntity = userService.createUser(userEntity, accountEntity);
//		return userEntity;
//	}
	
	@GetMapping(value = "/user-email/{email}")
	public List<UserEntity> getUserByEmail(@PathVariable("email") String email) {
		List<UserEntity>  user = userService.findUserByEmail(email);
		return user;
	}
	
	@GetMapping(value = "/user/{email}/{password}")
	public UserEntity getUserByEmailAndPassword(@PathVariable("email") String email, @PathVariable("password") String password) {
		UserEntity user = userService.getUserByEmailAndPassword(email, password);
		return user;
	}
	
	@GetMapping(value = "/user-password/{email}")
	public void resetPassword(@PathVariable("email") String email) {
//		userService.resetPassword(email, javaMailSender);
	}
	
	@GetMapping(value = "/signup-code/{email}")
	public void getSignUpCode(@PathVariable("email") String email) {
		tempStorageService.getSignUpCode(email, javaMailSender);
	}
	
	@GetMapping(value= "/code/{email}/{code}")
	public boolean confirmCode( @PathVariable("email") String email, @PathVariable("code") String code) {
		return tempStorageService.confirmCode(email, code);
	}
	
	@PutMapping(value = "/user/{id}")
	public UserEntity updateUserID(@RequestBody UserEntity user, @PathVariable("id") Long id) {
		user.setId(id);
		return userService.updateUser(user);
	}
	@PutMapping(value="/account/{email}")
	public AccountEntity changePassword(@RequestBody AccountEntity account, @PathVariable("email") String email) {
		return userService.changePassword(account,email);
	}
	@GetMapping("/listuser")
	public List<UserEntity> getListUser(){
		return userService.getListUser();
	}
	@GetMapping("/listuser/{type}")
	public List<UserEntity> getListUserbyType(@PathVariable("type") Long type){
		return userService.getListUserbyType(type);
	}
	@GetMapping("/listuser/{type}/{status}")
	public List<UserEntity> getListUserbyTypeandStatus(@PathVariable("type") Long type, @PathVariable("status") boolean status){
		return userService.getListUserbyTypeandStatus(type, status);
	}
	@PutMapping("/change-status-user/{userid}")
	public UserEntity statusUser(@PathVariable("userid") Long userid) {
		return userService.statusUser(userid);
	}
	@GetMapping("/listuser-search/{search}")
	public List<UserEntity> getListUserbySearch(@PathVariable("search") String search){
		return userService.getListUserbySearch(search);
	}

	
	//get user by id
	@GetMapping(value = "/user")
	public UserEntity getUserById(@RequestParam("userid") Long userid) {
		UserEntity user = userService.findUserById(userid);
		return user;
				
	}
}
