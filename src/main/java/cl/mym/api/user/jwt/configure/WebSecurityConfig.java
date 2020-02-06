package cl.mym.api.user.jwt.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import cl.mym.api.user.jwt.exception.CustomAuthenticationEntryPoint;
import cl.mym.api.user.jwt.secutiry.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()//
				.antMatchers(HttpMethod.POST, "/api/login/").permitAll()//
				.antMatchers(HttpMethod.POST, "/api/users/").permitAll()//
				.antMatchers("/h2-console/**/**").permitAll().anyRequest().authenticated();

		http.exceptionHandling().accessDeniedPage("/login");

		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

	}

	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/v2/api-docs").antMatchers("/swagger-resources/**").antMatchers("/swagger-ui.html")
				.antMatchers("/configuration/**").antMatchers("/webjars/**").antMatchers("/public").and().ignoring()
				.antMatchers("/h2-console/**/**");
		;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}

}

