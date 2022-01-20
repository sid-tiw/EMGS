package com.paytmbank.middleware.emgs.service;

import com.paytmbank.middleware.emgs.entity.Leave;
import com.paytmbank.middleware.emgs.exception.RequestError;
import com.paytmbank.middleware.emgs.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LeaveService {
    @Autowired
    LeaveRepository leaveRepo;

    public void save(Leave lv) {
        leaveRepo.save(lv);
    }

    /* Create is for creating new leaves. */
    public void create(Leave lv) throws Exception {
        if (lv.getStartDate() == null) throw new RequestError("Invalid Start date!!");
        if (lv.getEndDate() == null) throw new RequestError("Invalid End date!!");
        this.save(lv);
    }

    /* List all by pagination */
    public Page<Leave> listAll() {
        return leaveRepo.findAll(Pageable.ofSize(10));
    }
}
