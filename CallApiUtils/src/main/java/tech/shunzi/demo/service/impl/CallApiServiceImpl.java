package tech.shunzi.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tech.shunzi.demo.service.CallApiService;

@Service
public class CallApiServiceImpl implements CallApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> get(String url) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toUri(), HttpMethod.GET, requestEntity,
                String.class);
        return response;
    }

    @Override
    public ResponseEntity<String> post(String url, HttpEntity request) {
        ResponseEntity<String> response = restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toUri(), HttpMethod.POST, request,
                String.class);
        return response;
    }

    @Override
    public ResponseEntity<String> put(String url, HttpEntity request) {
        ResponseEntity<String> response = restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toUri(), HttpMethod.PUT, request,
                String.class);
        return response;    }

    @Override
    public ResponseEntity<String> delete(String url, HttpEntity request) {
        ResponseEntity<String> response = restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toUri(), HttpMethod.DELETE, request,
                String.class);
        return response;
    }
}
