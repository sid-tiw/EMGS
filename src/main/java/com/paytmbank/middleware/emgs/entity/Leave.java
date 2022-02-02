package com.paytmbank.middleware.emgs.entity;

import javax.persistence.*;

import com.paytmbank.middleware.emgs.dto.LeaveDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "`leave`")
public class Leave {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long lid;
	private String reason;
	private Date startDate;
	private Date endDate;

	private boolean isPaid;
	
	@OneToOne(mappedBy = "lid")
	private Employee emp; // the employee who has taken this leave.

	public Leave(LeaveDTO leaveDTO) {
		lid = leaveDTO.getLid();
		reason = leaveDTO.getReason();
		startDate = leaveDTO.getStartDate();
		endDate = leaveDTO.getEndDate();
		isPaid = leaveDTO.isPaid();
	}
}
