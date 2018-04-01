package com.sdsu.edu.cms.authservice;

import com.sdsu.edu.cms.authservice.util.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.sdsu.edu.cms.authservice")
@ComponentScan("com.sdsu.edu.cms")
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Component
	static class ReqInterceptor implements RequestInterceptor{

		@Override
		public void apply(RequestTemplate template) {
			template.header(Constants.INTERNAL_TOKEN, Constants.INTERNAL_TOKEN_VALUE);
		}
	}

}
