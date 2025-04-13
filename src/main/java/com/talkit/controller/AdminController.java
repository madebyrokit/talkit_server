package com.talkit.controller;

import com.talkit.entity.Member;
import com.talkit.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/members")
    public ResponseEntity<?> getMemberList() {
        return ResponseEntity.ok().body(adminService.getMemberList());
    }
}
