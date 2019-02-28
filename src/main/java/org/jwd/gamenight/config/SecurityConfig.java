package org.jwd.gamenight.config;

import java.util.Collections;

import javax.annotation.Resource;

import org.jwd.gamenight.entity.account.Account;
import org.jwd.gamenight.entity.account.Authority;
import org.jwd.gamenight.repository.AccountRepository;
//import com.example.entity.User;
//import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages =
{ "org.jwd.gamenight" })
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

	@Resource
	public ApplicationContext context;

	@Autowired
	private AccountRepository userRepository;

	@Override
	public void configure(final WebSecurity web) throws Exception
	{
		web

				.ignoring().antMatchers("/view/**").antMatchers("/asset/**")
				.antMatchers("/style/**").antMatchers("/fonts/**")
				.antMatchers("/images/**").antMatchers("/avatars/**")
				.antMatchers("/script/**");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception
	{
		auth.authenticationProvider(new AuthenticationProvider()
		{
			public Authentication authenticate(Authentication authentication)
					throws AuthenticationException
			{
				String username = (String) authentication.getPrincipal();
				String providedPassword = (String) authentication
						.getCredentials();

				Account user = userRepository.findByUsername(username);

				if (user == null || !passwordEncoder().matches(providedPassword,
						user.getPassword()))
				{
					throw new BadCredentialsException(
							"Username/Password does not match for "
									+ authentication.getPrincipal());
				}

				String role = null;

				for (Authority authority : user.getAuthorities())
				{
					role = authority.getAuthority();
				}

				return new UsernamePasswordAuthenticationToken(username,
						providedPassword, Collections
								.singleton(new SimpleGrantedAuthority(role)));
			}

			@Override
			public boolean supports(Class<?> authentication)
			{
				return true;
			}
		});
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
				.antMatchers("/login", "/registration", "/registrationSave",
						"/home", "/", "/resources/**", "/avatars/**", "style/**")
				.permitAll() // #4
				// .antMatchers("/admin*").hasRole("ADMIN") // #6
				.anyRequest().authenticated() // 7
				.and().formLogin() // #8
				.loginPage("/login") // #9
				.permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login"); // #5
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
