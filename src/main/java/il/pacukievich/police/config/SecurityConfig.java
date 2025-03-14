package il.pacukievich.police.config;

import il.pacukievich.police.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

		private final CustomUserDetailsService customUserDetailsService;

		public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
				this.customUserDetailsService = customUserDetailsService;
		}

		@Bean
		public PasswordEncoder passwordEncoder() {
				return new BCryptPasswordEncoder();
		}

		@Bean
		public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
				return http.getSharedObject(AuthenticationManagerBuilder.class)
								.userDetailsService(customUserDetailsService)
								.passwordEncoder(passwordEncoder())
								.and()
								.build();
		}


//		@Bean
//		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//				http
//								.csrf(csrf -> csrf.disable())
//								.authorizeHttpRequests(auth -> auth
//												.requestMatchers("/public/**").permitAll()
//												.requestMatchers("/submit-report/**").permitAll()
//												.requestMatchers("/admin/**").hasRole("ADMIN")
//												.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//												.anyRequest().authenticated()
//								)
//								.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//								.httpBasic(httpBasic -> httpBasic.disable())
//								.formLogin(Customizer.withDefaults());
//
//				return http.build();
//		}
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
						.authorizeRequests()
						.requestMatchers("/**").hasRole("ADMIN")
						.requestMatchers("/public/**").permitAll()
						.requestMatchers("/api/crimes/**").permitAll()
						.requestMatchers("/submit-report/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
						.anyRequest().authenticated()
						.and()
						.formLogin()
						.permitAll();

		return http.build();
}
}
