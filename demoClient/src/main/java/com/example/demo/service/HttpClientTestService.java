package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Verification;

@Service
public class HttpClientTestService {

    public Verification executeRequest(String id)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException,
            IOException, UnrecoverableKeyException, KeyManagementException {

        String url = "https://localhost/thin-server/verifications/" + id;

        SSLContextBuilder SSLBuilder = SSLContexts.custom();

        File file = ResourceUtils.getFile("classpath:mytruststore.jks");
        SSLBuilder.loadTrustMaterial(file, "changeit".toCharArray());

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream keyStoreInput = new FileInputStream(ResourceUtils.getFile("classpath:client1.p12"));
        keyStore.load(keyStoreInput, "pwd1".toCharArray());
        SSLBuilder.loadKeyMaterial(keyStore, "pwd1".toCharArray());

        SSLContext sslContext = SSLBuilder.build();
        HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(client);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        ResponseEntity<Verification> result = restTemplate.exchange(url, HttpMethod.GET, null, Verification.class);

        return result.getBody();
    }

}
