package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Data;
import com.example.demo.model.Verification;

@RestController
@RequestMapping("/thin-server/verifications")
public class ThinServerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThinServerController.class);

    protected static final String CERTIFICATE_ISSUER_HEADER = "demo.certificate.issuer";
    protected static final String CERTIFICATE_SUBJECT_HEADER = "demo.certificate.subject";
    protected static final String CERTIFICATE_VERIFY_HEADER = "demo.certificate.verify";

    @GetMapping("/{id}")
    public Verification getVerification(@PathVariable String id, HttpServletRequest request) {
        LOGGER.info(CERTIFICATE_ISSUER_HEADER + ": " + request.getHeader(CERTIFICATE_ISSUER_HEADER));
        LOGGER.info(CERTIFICATE_SUBJECT_HEADER + ": " + request.getHeader(CERTIFICATE_SUBJECT_HEADER));
        LOGGER.info(CERTIFICATE_VERIFY_HEADER + ": " + request.getHeader(CERTIFICATE_VERIFY_HEADER));

        Verification verification = new Verification();
        Data data = new Data();
        data.setId(id);
        verification.setDocumentType("ES");
        verification.setData(data);
        return verification;
    }
}
