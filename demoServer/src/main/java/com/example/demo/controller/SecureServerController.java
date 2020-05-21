package com.example.demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Data;
import com.example.demo.model.Verification;

@RestController
@RequestMapping("/server/verifications")
public class SecureServerController {

    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public Verification GetCustomer(@PathVariable String id) {
        Verification verification = new Verification();
        Data data = new Data();
        data.setId(id);
        verification.setDocumentType("ES");
        verification.setData(data);
        return verification;
    }

}
