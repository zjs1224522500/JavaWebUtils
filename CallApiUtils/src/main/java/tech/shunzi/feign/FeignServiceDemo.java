package tech.shunzi.feign;

import feign.Param;
import feign.RequestLine;

/**
 * Version:v1.0 (description:  ) Date:2018/5/19 0019  Time:18:18
 */
public interface FeignServiceDemo {

	@RequestLine("GET /regeocoding?l={l}")
	String getLocation(@Param("l") String location);
}
