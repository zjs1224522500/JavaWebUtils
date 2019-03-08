package tech.shunzi.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shunzi.demo.service.CallApiService;

import java.util.HashSet;
import java.util.Set;

@RestController
public class GetNMSLResponseController {

    @Autowired
    private CallApiService callApiService;

    @GetMapping("/spider")
    public ResponseEntity<String> get()
    {
        Set<String> results = new HashSet<>();

        for (int i = 0; i < 1000; i++)
        {
            String body = callApiService.get("https://nmsl.shadiao.app/").getBody();
            results.add(body);
        }
        System.out.println(results);
        System.out.println(results.size());
        return new ResponseEntity<>(results.toString(), HttpStatus.OK);
    }
}
