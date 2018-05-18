package tech.shunzi.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shunzi.demo.service.CallApiService;

@RestController
public class CallApiController {

    @Autowired
    private CallApiService callApiService;

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
}
