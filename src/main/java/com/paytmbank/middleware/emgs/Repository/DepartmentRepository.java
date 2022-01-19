package com.paytmbank.middleware.emgs.Repository;

import com.paytmbank.middleware.emgs.entity.Department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
	
}
