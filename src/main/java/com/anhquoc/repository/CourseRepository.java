package com.anhquoc.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.UserEntity;


@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>{
	public List<CourseEntity> findAllByUser(UserEntity user, Pageable pageable);
	public List<CourseEntity> findAllByUser(UserEntity user);
	public CourseEntity findOneById(Long id);
}
