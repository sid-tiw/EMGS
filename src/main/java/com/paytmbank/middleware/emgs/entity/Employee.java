package com.paytmbank.middleware.emgs.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.paytmbank.middleware.emgs.dto.EmployeeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Employee {
	@Id
	private String eid;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String phone;
	private String password;
	private String role;

	private String fname;
	private String sname;
	private Timestamp createdDate;
	private Timestamp deletedDate;
	private boolean isActive;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "dpt")
	private Department dpt;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "pid")
	private Project pid;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "lid")
	private Leave lid;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "rid")
	private Role rid;

	@OneToMany(mappedBy = "raisedBy")
	private List<Ticket> tickets;

	public Employee(EmployeeDTO employeeDTO) {
		eid = employeeDTO.getEid();
		email = employeeDTO.getEmail();
		phone = employeeDTO.getPhone();
		password = employeeDTO.getPassword();
		role = employeeDTO.getRole();
		fname = employeeDTO.getFname();
		sname = employeeDTO.getSname();
		createdDate = employeeDTO.getCreatedDate();
		deletedDate = employeeDTO.getDeletedDate();
		isActive = employeeDTO.isActive();
		dpt = employeeDTO.getDpt();
		pid = employeeDTO.getPid();
		lid = employeeDTO.getLid();
		rid = employeeDTO.getRid();
		tickets = employeeDTO.getTickets();
	}
}
