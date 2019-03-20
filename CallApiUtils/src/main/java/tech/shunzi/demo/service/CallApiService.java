package tech.shunzi.demo.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public interface CallApiService {

    ResponseEntity<String> get(String url);

    ResponseEntity<String> post(String url, HttpEntity request);

    ResponseEntity<String> put(String url, HttpEntity request);

    ResponseEntity<String> delete(String url, HttpEntity request);

    String testConnectionPool();

    ResponseEntity<Object> getObject(String url, HttpEntity request);

    ResponseEntity<String> getString(String url, HttpEntity request);

}
