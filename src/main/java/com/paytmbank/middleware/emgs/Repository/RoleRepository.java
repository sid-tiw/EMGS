package com.paytmbank.middleware.emgs.repository;

import com.paytmbank.middleware.emgs.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	
}
