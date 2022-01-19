package com.paytmbank.middleware.emgs.service;

import javax.transaction.Transactional;

import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.exception.EmployeeAlreadyPresent;
import com.paytmbank.middleware.emgs.exception.EmployeeNotFound;
import com.paytmbank.middleware.emgs.exception.RequestError;
import com.paytmbank.middleware.emgs.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.regex.*;

@Service
@Transactional
public class EmployeeService {
	@Autowired
	private EmployeeRepository empRepo;

	public void save(Employee emp) {
		empRepo.save(emp);
	}

	public List<Employee> listAll() {
		return empRepo.findAll();
	}

	public Employee find(String eid) throws EmployeeNotFound {
		if (!empRepo.existsById(eid)) throw new EmployeeNotFound("No such employee ID.");
		Employee emp = empRepo.findById(eid).get();
		return emp;
	}

	/* To create new employee. An employee object is passed, and then checks are done inside this function, before ultimately calling
	the save function inside here. */
	public void create(Employee emp) throws Exception {
		/* Checks start here */

		// If already found, then throw an error.
		if (empRepo.existsById(emp.getEid())) throw new EmployeeAlreadyPresent("Error!! Another employee with same id exists.");
		// phone number size should be exactly 10.
		if (emp.getPhone().length() != 10) throw new RequestError("Invalid phone number!!");
		// validate email here.
		Pattern pt = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pt.matcher(emp.getEmail());
		if (!matcher.find()) throw new RequestError("Invalid Email Address!!");

		/* Checks end here */

		/* Routine sets start here */
		emp.setActive(true);
		emp.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		/* Routine sets end here */

		this.save(emp);
	}
}
