package com.anhquoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anhquoc.entity.AccountEntity;
import com.anhquoc.entity.UserEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
	public AccountEntity save(AccountEntity account);
	public AccountEntity findOneByUser(UserEntity user);
//	public AccountEntity fib
}
