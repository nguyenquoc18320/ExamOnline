package com.anhquoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long>{
	public TestEntity findOneById(Long id);
	public List<TestEntity> findAllByCourse(CourseEntity course);
}
