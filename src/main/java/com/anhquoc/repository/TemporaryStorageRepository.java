package com.anhquoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anhquoc.entity.TemporaryStorage;

@Repository
public interface TemporaryStorageRepository extends JpaRepository<TemporaryStorage, String>{
	public TemporaryStorage findOneBySubject(String subject);
}
