package tech.shunzi.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import tech.shunzi.demo.service.CallApiService;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestShadiaoWeb {

    @Autowired
    private CallApiService callApiService;

    @Test
    public void testShadiao()
    {
        Set<String> results = new HashSet<>();
        for (int i = 0; i < 1000; i++)
        {
            String body = callApiService.get("https://nmsl.shadiao.app/").getBody();
            System.out.println(body);
            results.add(body);
        }
        System.out.println(results);
        System.out.println(results.size());
    }

    @Test
    public void testShadiaoHJeader()
    {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-Key", "e1a3bfd0");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = callApiService.getString("https://my.api.mockaroo.com/mockapitest", requestEntity);
        ResponseEntity<Object> responseObj = callApiService.getObject("https://my.api.mockaroo.com/mockapitest", requestEntity);
        System.out.println(response.getHeaders());
        System.out.println(responseObj.getHeaders());
    }



}
