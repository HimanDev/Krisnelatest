package com.krisnela.test.mvp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.krisnela.test.mvp.component.JwtFilter;
import com.krisnela.test.mvp.component.SimpleCORSFilter;
import com.krisnela.test.mvp.dto.User;

@SpringBootApplication
@ComponentScan
public class KrisnelatestApplication {
	@Autowired
	User user;

	public static void main(String[] args) {
		SpringApplication.run(KrisnelatestApplication.class, args);
	}
	   @Bean
	    public FilterRegistrationBean jwtFilter() {
	        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	        registrationBean.setFilter(new JwtFilter());
	        registrationBean.addUrlPatterns("/api/*","/Employee/");

	        return registrationBean;
	    }
	   @Bean
	   public User user(){
		   final User user=new User();
		   user.setName("Hello Himan");
		   return user;
	   }
	
}
	