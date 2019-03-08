package tech.shunzi.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
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

}
