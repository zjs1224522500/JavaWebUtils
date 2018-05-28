package tech.shunzi.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
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

import feign.Feign;
import feign.Request;
import feign.Retryer;
import tech.shunzi.feign.FeignServiceDemo;
import tech.shunzi.interceptor.TokenInterceptor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class Config {

	private boolean ifNeedTokenToCallApi = false;

	@Autowired
	private Environment env;

	@Autowired
	private TokenInterceptor tokenInterceptor;

	@Bean
	public FeignServiceDemo feignServiceDemo() {
		return Feign.builder().options(new Request.Options(1000, 3500))
				.retryer(new Retryer.Default(5000, 5000, 3))
				.target(FeignServiceDemo.class, "http://gc.ditu.aliyun.com");
	}

	@Bean(name = "restTemplate")
	public RestTemplate restTemplate()
			throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

		//CloseableHttpClient httpClient = httpClient();
		CloseableHttpClient httpClient = buildSSLClient();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		if (ifNeedTokenToCallApi) {
			restTemplate.getInterceptors().add(tokenInterceptor);
		}
		return restTemplate;
	}

	@Bean(name = "restTemplateFromConnectionPool")
	public RestTemplate restTemplateFromConnectionPool()
			throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		CloseableHttpClient httpClient = buildClientFromConnectionPool(connectionManager());
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		if (ifNeedTokenToCallApi) {
			restTemplate.getInterceptors().add(tokenInterceptor);
		}
		return restTemplate;
	}

	@Bean
	public PoolingHttpClientConnectionManager connectionManager() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// set max total connection is 200
		cm.setMaxTotal(200);
		// set max connection is 20 per route
		cm.setDefaultMaxPerRoute(20);
		return cm;
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

	private CloseableHttpClient httpClient() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setDefaultMaxPerRoute(1);
		connectionManager.setMaxTotal(20);
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
				.setConnectionManager(connectionManager);
		// Set proxy
		String proxy = System.getenv("https_proxy");
		if (StringUtils.isNotBlank(proxy)) {
			proxy = proxy.replace("http://", "");
			int portSplitter = proxy.lastIndexOf(':');
			if (NumberUtils.isNumber(proxy.substring(proxy.lastIndexOf(':') + 1))) {
				String proxyHost = proxy.substring(0, portSplitter);
				Integer proxyPort = Integer.valueOf(proxy.substring(portSplitter + 1));
				httpClientBuilder.setProxy(new HttpHost(proxyHost, proxyPort, "https"));
			}
		}

		// set proxy if run production env. And the proxy can convert https -> http
		if (null != env.getProperty("spring.config.name") || StringUtils
				.join(env.getActiveProfiles()).contains("local")) {
			httpClientBuilder.setProxy(new HttpHost("45.78.12.52", 8080, "http"));
		}

		// Build
		return httpClientBuilder.build();
	}

	private CloseableHttpClient buildSSLClient()
			throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
				.loadTrustMaterial(null, acceptingTrustStrategy).build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		return httpClient;
	}

	private CloseableHttpClient buildClientFromConnectionPool(PoolingHttpClientConnectionManager cm)
			throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		// set ssl
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
				.loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
				.setRetryHandler(httpRequestRetryHandler(5)).setSSLSocketFactory(csf).build();
		return httpClient;
	}

	private HttpRequestRetryHandler httpRequestRetryHandler(final int tryTimes) {
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {

			@Override
			public boolean retryRequest(IOException exception, int executionCount,
					HttpContext httpContext) {
				if (executionCount >= tryTimes) {
					return false;
				}
				// 如果服务器丢掉了连接，那么就重试
				if (exception instanceof NoHttpResponseException) {
					return true;
				}
				// 不要重试SSL握手异常
				if (exception instanceof SSLHandshakeException) {
					return false;
				}
				// 超时
				if (exception instanceof InterruptedIOException) {
					return false;
				}
				// 目标服务器不可达
				if (exception instanceof UnknownHostException) {
					return true;
				}
				// 连接被拒绝
				if (exception instanceof ConnectTimeoutException) {
					return false;
				}
				// SSL握手异常
				if (exception instanceof SSLException) {
					return false;
				}
				HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
				HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};
		return httpRequestRetryHandler;
	}
}
