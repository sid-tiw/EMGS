package com.paytmbank.middleware.emgs.Repository;

import com.paytmbank.middleware.emgs.entity.Leave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, String> {
	
}
