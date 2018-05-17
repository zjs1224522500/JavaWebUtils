package tech.shunzi.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tech.shunzi.interceptor.TokenInterceptor;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
@EnableAutoConfiguration
public class Config {

    private boolean ifNeedTokenToCallApi = false;

    @Autowired
    private Environment env;

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Bean
    public RestTemplate restTemplate() {

        CloseableHttpClient httpClient = httpClient();
        //CloseableHttpClient httpClient = buildSSLClient();
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        if(ifNeedTokenToCallApi) {
            restTemplate.getInterceptors().add(tokenInterceptor);
        }
        return restTemplate;
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    private CloseableHttpClient httpClient()
    {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(1);
        connectionManager.setMaxTotal(20);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(connectionManager);
        // Set proxy
        String proxy = System.getenv("https_proxy");
        if (StringUtils.isNotBlank(proxy))
        {
            proxy = proxy.replace("http://", "");
            int portSplitter = proxy.lastIndexOf(':');
            if (NumberUtils.isNumber(proxy.substring(proxy.lastIndexOf(':') + 1)))
            {
                String proxyHost = proxy.substring(0, portSplitter);
                Integer proxyPort = Integer.valueOf(proxy.substring(portSplitter + 1));
                httpClientBuilder.setProxy(new HttpHost(proxyHost, proxyPort, "https"));
            }
        }

        // set proxy if run production env. And the proxy can convert https -> http
        if (null != env.getProperty("spring.config.name") || StringUtils.join(env.getActiveProfiles()).contains("local"))
        {
            httpClientBuilder.setProxy(new HttpHost("45.78.12.52", 8080, "http"));
        }

        // Build
        return httpClientBuilder.build();
    }

    private CloseableHttpClient buildSSLClient(){
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();
        return httpClient;
    }
}
