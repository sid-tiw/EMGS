package com.paytmbank.middleware.emgs.repository;

import com.paytmbank.middleware.emgs.entity.Project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {
	
}
