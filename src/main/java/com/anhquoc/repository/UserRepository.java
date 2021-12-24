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
	public  UserEntity findOneByEmail(String email);
	public UserEntity findOneById(Long id);
	public List<UserEntity> findByName(String name);
//	
//    @Query(value= "SELECT * FROM user WHERE name = :name", nativeQuery = true)
//    List<UserEntity> findUserByName(@Param("name") String name);
	@Query(value = "SELECT u FROM UserEntity u "
			+ " WHERE u.role.id = ?1")
	public List<UserEntity> findAllbyType(Long type);
	
	@Query(value = "SELECT u FROM UserEntity u "
			+ " WHERE u.role.id = ?1 AND u.status = ?2")
	public List<UserEntity> findAllbyTypeStatus(Long type, boolean status);
	
	@Query(value = "SELECT u FROM UserEntity u "
			+ " WHERE u.status = ?1")
	public List<UserEntity> findAllbyTypeStatus2( boolean status);
	
	@Query(value = "SELECT u FROM UserEntity u "
			+ " WHERE u.name LIKE %?1%")
	public List<UserEntity> findAllbySearch( String search);
	
	
	//get total users for admin
	@Query(value="SELECT COUNT(u) FROM UserEntity u")
	public int getTotalUsers();
	
	//get total active users for admin
	@Query(value="SELECT COUNT(u) FROM UserEntity u "
			+ " WHERE u.status=True")
	public int getTotalActiveUsers();
	
	//get total active users for admin
	@Query(value="SELECT COUNT(u) FROM UserEntity u "
			+ " WHERE u.status=False")
	public int getTotalDisableUsers();
}
