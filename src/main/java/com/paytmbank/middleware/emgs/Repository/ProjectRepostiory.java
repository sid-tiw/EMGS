package com.paytmbank.middleware.emgs.Repository;

import com.paytmbank.middleware.emgs.entity.Project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepostiory extends JpaRepository<Project, String> {
	
}
