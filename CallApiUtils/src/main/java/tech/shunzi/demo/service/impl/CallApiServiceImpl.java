package tech.shunzi.demo.service.impl;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;

import tech.shunzi.demo.service.CallApiService;
import tech.shunzi.utils.IdleConnectionEvictor;

@Service
public class CallApiServiceImpl implements CallApiService {

	@Resource(name = "restTemplate")
	private RestTemplate restTemplate;

	@Resource(name = "restTemplateFromConnectionPool")
	private RestTemplate restTemplateFromConnectionPool;

	@Autowired
	private PoolingHttpClientConnectionManager cm;

	@Override
	public ResponseEntity<String> get(String url) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

		ResponseEntity<String> response = restTemplate
				.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toUri(), HttpMethod.GET,
						requestEntity, String.class);
		return response;
	}

	@Override
	public ResponseEntity<String> post(String url, HttpEntity request) {
		ResponseEntity<String> response = restTemplate
				.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toUri(), HttpMethod.POST,
						request, String.class);
		return response;
	}

	@Override
	public ResponseEntity<String> put(String url, HttpEntity request) {
		ResponseEntity<String> response = restTemplate
				.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toUri(), HttpMethod.PUT,
						request, String.class);
		return response;
	}

	@Override
	public ResponseEntity<String> delete(String url, HttpEntity request) {
		ResponseEntity<String> response = restTemplate
				.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toUri(), HttpMethod.DELETE,
						request, String.class);
		return response;
	}

	@Override
	public String testConnectionPool() {
		long withoutPool = callApi100Times(restTemplate);
		long withPool = callApi100Times(restTemplateFromConnectionPool);
		String withPoolStr = "with pool :" + withPool + "\n";
		String withoutPoolStr = "without pool :" + withoutPool;
		System.out.println(withoutPoolStr + withoutPoolStr);
		new IdleConnectionEvictor(cm).start();
		return withPoolStr + withoutPoolStr;
	}

	private long callApi100Times(RestTemplate restTemplate) {
		String realUrl = "https://free-api.heweather.com/v5/forecast?city=CN101080101&key=5c043b56de9f4371b0c7f8bee8f5b75e";
		int failure = 0;

		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			if (restTemplate.getForEntity(realUrl, String.class).getStatusCode().is2xxSuccessful()) {
				System.out.println("Success : 【" + i + "】");
			} else {
				System.out.println("Fail : 【" + i + "】");
				failure++;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		System.out.println("Fail total : " + failure);
		return end - start;
	}
}
