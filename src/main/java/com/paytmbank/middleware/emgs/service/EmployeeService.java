package com.paytmbank.middleware.emgs.service;

import javax.transaction.Transactional;

import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.entity.Project;
import com.paytmbank.middleware.emgs.exception.*;
import com.paytmbank.middleware.emgs.repository.EmployeeRepository;

import com.paytmbank.middleware.emgs.repository.ProjectRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	@Autowired
	private ProjectRepository projectRepo;

	public void save(Employee emp) {
		empRepo.save(emp);
	}

	public Page<Employee> listAll() {
		return empRepo.findAll(Pageable.ofSize(10));
	}

	public Employee find(String eid) throws EmployeeNotFound {
		if (!empRepo.existsById(eid)) throw new EmployeeNotFound("No such employee ID.");
		Employee emp = empRepo.findById(eid).get();
		return emp;
	}

	public Employee findByPhone(String phone) throws EmployeeNotFound {
		if (!empRepo.existsByPhone(phone)) throw new EmployeeNotFound("No employee with the given phone number.");
		Employee emp = empRepo.findByPhone(phone);
		return emp;
	}

	public Employee findByEmail(String email) throws EmployeeNotFound {
		if (!empRepo.existsByEmail(email)) throw new EmployeeNotFound("No employee with the given email.");
		Employee emp = empRepo.findByEmail(email);
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

	/* Delete Employee function. First it finds which employee to delete
    and if not found, then EmployeeNotFound error is returned, otherwise the
    employee is deleted.
     */
	public void delete(String eid) throws EmployeeNotFound {
		if (!empRepo.existsById(eid)) throw new EmployeeNotFound("No such employee ID.");
		Employee emp = empRepo.getById(eid);
		/* Just set the deleted date and inactive in employee. */
		emp.setDeletedDate(new Timestamp(System.currentTimeMillis()));
		emp.setActive(false);
	}

	/* Update Employee function. Anything can be updated, except their eid.
	 */
	public void update(Employee emp) throws Exception {
		if (!empRepo.existsById(emp.getEid())) throw new EmployeeNotFound("No such employee ID.");
		empRepo.delete(empRepo.getById(emp.getEid()));
		this.create(emp);
	}

	/* Remove project from employee.
	If no project is found in the employee, then return NoAssignedProject else delete the project
	 */
	public void dropProject(String eid) throws Exception {
		if (!empRepo.existsById(eid)) throw new EmployeeNotFound("No such employee ID.");
		Employee emp = empRepo.getById(eid);
		Project prj = emp.getPid();
		if (prj == null) throw new NoAssignedProject("The employee doesn't have any project assigned.");
		emp.setPid(null);
	}

	/* Add project to employee.
	 */
	public Employee addProject(String data) throws Exception {
		JSONObject jsonObject = new JSONObject(data);
		String eid = jsonObject.getString("eid");
		String pid = jsonObject.getString("pid");

		if (!empRepo.existsById(eid)) throw new EmployeeNotFound("No such employee ID.");
		if (!projectRepo.existsById(pid)) throw new ProjectNotFound("No such project ID.");

		Employee emp = empRepo.getById(eid);
		if (emp.getPid() != null) throw new ProjectAlreadyPresent("Project already assigned to the employee.");
		Project prj = projectRepo.getById(eid);
		emp.setPid(prj);
		empRepo.save(emp);

		return emp;
	}
}
