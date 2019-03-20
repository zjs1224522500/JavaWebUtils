package tech.shunzi.demo.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shunzi.demo.service.CallApiService;
import tech.shunzi.feign.FeignServiceDemo;

@RestController
public class CallApiController {

    @Autowired
    private CallApiService callApiService;

    @Autowired
    private FeignServiceDemo feignServiceDemo;

    String host = "https://my.api.mockaroo.com/";
    String key = "?key=e1a3bfd0";

    @GetMapping(value = "/test")
    public ResponseEntity<String> get(String url)
    {
        String realUrl = host + url + key;
        return callApiService.get(realUrl);
    }

    @GetMapping(value = "/https")
    public ResponseEntity<String> get()
    {
        String realUrl = "https://free-api.heweather.com/v5/forecast?city=CN101080101&key=5c043b56de9f4371b0c7f8bee8f5b75e";
        return callApiService.get(realUrl);
    }

    @GetMapping(value = "/test/pool")
    public String testConnectionPool()
    {
        return callApiService.testConnectionPool();
    }

    @GetMapping(value = "/test/feign")
    public String testFeign()
    {
        return feignServiceDemo.getLocation("39.938133,116.395739");
    }

    @GetMapping(value = "/string")
    public String getString()
    {
        JSONObject object = new JSONObject();
        object.put("test", "test");
        return object.toString();
    }

    @GetMapping(value = "/object")
    public ResponseEntity<Object> getObject()
    {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<Object> responseEntity = callApiService.getObject("http://localhost:8081/string", requestEntity);
        System.out.println(responseEntity.getHeaders().getContentType());
        return responseEntity;
    }

    @GetMapping(value = "/string-resp")
    public ResponseEntity<String> getStringResp()
    {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> responseEntity = callApiService.getString("http://localhost:8081/string", requestEntity);
        System.out.println(responseEntity.getHeaders().getContentType());
        return responseEntity;
    }
}
