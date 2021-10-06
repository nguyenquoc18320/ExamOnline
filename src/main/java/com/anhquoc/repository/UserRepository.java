package com.anhquoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anhquoc.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	public UserEntity save(UserEntity entity);
	public  List<UserEntity> findByEmail(String email);
	public UserEntity findOneById(Long id);
	public List<UserEntity> findByName(String name);
//	
//    @Query(value= "SELECT * FROM user WHERE name = :name", nativeQuery = true)
//    List<UserEntity> findUserByName(@Param("name") String name);
}
