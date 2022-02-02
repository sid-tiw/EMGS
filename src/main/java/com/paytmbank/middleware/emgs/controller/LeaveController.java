package com.paytmbank.middleware.emgs.controller;

import com.paytmbank.middleware.emgs.details.LeaveDetails;
import com.paytmbank.middleware.emgs.dto.LeaveDTO;
import com.paytmbank.middleware.emgs.entity.Leave;
import com.paytmbank.middleware.emgs.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaveController {
    @Autowired
    LeaveService lvService;

    @GetMapping("/leaves")
    public ResponseEntity<Page<Leave>> getAll() {
        return ResponseEntity.ok().body(lvService.listAll());
    }

    @PostMapping("/createLeave/")
    public ResponseEntity<LeaveDetails> createLeave(@RequestBody LeaveDTO lv) {
        try {
            lvService.create(lv.getLeave());
            return ResponseEntity.ok().body(new LeaveDetails(lv.getLeave()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LeaveDetails(e));
        }
    }
}
