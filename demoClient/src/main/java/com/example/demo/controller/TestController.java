package com.example.demo.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Verification;
import com.example.demo.service.HttpClientTestService;

@Controller
public class TestController {

    @Autowired
    private HttpClientTestService service;

    @GetMapping(value = "/client/verifications/{id}")
    public ResponseEntity<Verification> getVerification(@PathVariable String id)
            throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        Verification verification = service.executeRequest(id);
        return new ResponseEntity<>(verification, HttpStatus.OK);
    }

}
