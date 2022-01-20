package com.paytmbank.middleware.emgs.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	private String fname, sname;
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
}
