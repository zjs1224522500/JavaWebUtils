package tech.shunzi.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import tech.shunzi.security.JWTTokenUtil;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TokenInterceptor implements ClientHttpRequestInterceptor {

    private final ConcurrentMap<String, String> tokenCache = new ConcurrentHashMap<>();

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        String token;
        if (tokenCache.containsKey("token") && StringUtils.isNotEmpty(tokenCache.get("token"))
                && !JWTTokenUtil.isTokenExpired(tokenCache.get("token")))
        {
            token = tokenCache.get("token");
        }
        else
        {
            token = getToken();
            tokenCache.put("token", token);
        }
        HttpHeaders headers = httpRequest.getHeaders();
        headers.add("Authorization", token);
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

    private String getToken()
    {
        String token = "";
        return token;
    }
}
