package ca.sheridancollege.zhang240.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * Security configuration for access control.
 *
 * delete to ADMIN only
 * insert and update for USER and ADMIN
 * use BCrypt for password encrypting
 * 
 * @author Yue Zhang
 */
@Configuration 
@EnableWebSecurity
public class SecurityConfig {

	//get users login details from H2 database instead of from memory
	@Autowired
	private UserDetailsService userDetailsService;
	
	/**
     *
     * @throws Exception - show permission denied page when exception happens
     */
	@Bean
	//set the rules for different requests
	public SecurityFilterChain securityfilterChain(HttpSecurity http, 
								HandlerMappingIntrospector introspector) throws Exception {
		MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
		return http.authorizeHttpRequests(authorize -> authorize
				//only USER role can access the pages under /secure
				.requestMatchers(mvc.pattern("/secure/delete")).hasRole("ADMIN")
				.requestMatchers(mvc.pattern("/secure/**")).hasAnyRole("USER", "ADMIN")
				//everyone can access below pages
				.requestMatchers(mvc.pattern("/")).permitAll()
				.requestMatchers(mvc.pattern("/js/**")).permitAll()
				.requestMatchers(mvc.pattern("/css/**")).permitAll()
				.requestMatchers(mvc.pattern("/images/**")).permitAll()
				.requestMatchers(mvc.pattern("/javadoc/**")).permitAll()
				.requestMatchers(mvc.pattern("/permission-denied")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
				//All user can do insertCourse
				.requestMatchers(HttpMethod.POST, "/insertCourse").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.GET, "/deleteCourse").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/updateCourse").hasAnyRole("USER", "ADMIN")
				//deny all other requests by default
				.requestMatchers(mvc.pattern("/**")).denyAll())
			//let H2 console available for us
			.csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).disable())
			.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
			//everyone can access /login page the login form
			.formLogin(form -> form.loginPage("/login").permitAll())
			//navigate to permission-denied page if not USER role
			.exceptionHandling(exception -> exception.accessDeniedPage("/permission-denied"))
			//everyone can logout
			.logout(logout -> logout.permitAll()).build();
	}
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(auth -> auth
//                .anyRequest().permitAll()  // 允许所有请求
//            )
//            .csrf(csrf -> csrf.disable()) // 关闭 CSRF（用于表单方便测试）
//            .formLogin(login -> login.disable()) // 关闭默认登录页
//            .httpBasic(basic -> basic.disable()); // 关闭 Basic Auth
//
//        return http.build();
//    }
	
	//to encrypt password
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder;
	}
	
}
