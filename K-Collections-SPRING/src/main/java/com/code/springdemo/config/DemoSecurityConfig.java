package com.code.springdemo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // 설정파일을 만들기 위한 애노테이션 or Bean을 등록하기 위한 애노테이션
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired //의존성 주입 DI
	private DataSource securityDataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication().dataSource(securityDataSource);
		// 로그인에 성공하면 세션에 값이 담기게 됨.
	    // 따라서 브라우저를 다 끄고 다시 시작하지 않는 이상(세션을 끊지 않는 이상) 재실행 시 바로 home.jsp으로 가게 됨.
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	    	.authorizeRequests()
	    		.antMatchers("/css/**")
	    		.permitAll()
//	            .anyRequest().authenticated() // 모든 request를 인증서비스로 넘긴다. 시작화면이 로그인 창
	    									// mypage를 누르면 로그인이 돼있는지 확인, 로그인 돼있으면 지정된 페이지로 이동, 로그인 안돼있으면 로그인창으로
	            .antMatchers("/userPage/**").hasAnyRole("USER", "VIP", "VVIP", "ARTIST") //이 페이지에 관한 권한만 부여함
	            .antMatchers("/artistPage/**").hasRole("ARTIST")
	            .antMatchers("/adminPage/**").hasRole("ADMIN")
	            
	            .antMatchers("/vipPage/**").hasAnyRole("VIP", "VVIP", "ADMIN")
	    		.anyRequest().permitAll() // 어떤 권한이든 다 적용
	    		// 만약, ADMIN과 MANAGER가 상하관계에 있고 ADMIN이 상사라 더 높은 권한을 부여받아야 한다면, ADMIN을 "/leaders/systems/**" 이런 식으로 설정할 수 있다
	        .and()
	        .formLogin()
	            .loginPage("/showMyLoginPage") //로그인창 Url 설정
	            .loginProcessingUrl("/authenticateTheUser") // jsp에서 action으로 넘겨주는 파라미터를 여기서 처리함.
	            .permitAll()
	        .and()
	        .logout()
	        	.logoutSuccessUrl("/logout")// 로그아웃이 성공했을때 페이지 설정, 그냥 Url만 하면 로그인 창으로 감
	        	.permitAll()
	    	.and()
	    	.exceptionHandling().accessDeniedPage("/access-denied");
	}

}
