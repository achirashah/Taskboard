package edu.neu.shah.taskboard;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class TaskboardSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll(true);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**", "/img/**", "/css/**").anyRequest();
	}

//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		@SuppressWarnings("deprecation")
//		UserDetails user = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN").build();
//
//		return new InMemoryUserDetailsManager(user);
//	}
}