package com.paytmbank.middleware.emgs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Employee {
	@Id
	private String eid;
	private String email;
	private String phone;
	private String fname, sname;
	
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
	@OneToMany
	@JoinColumn(name = "rid")
	private Role rid;
}
