package jp.co.sss.cytech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jp.co.sss.cytech.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
	
	private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // URLごとのアクセス制御
        	.userDetailsService(userDetailsService)
        	.authorizeHttpRequests(auth -> auth
    		    .requestMatchers(
    		        "/login",
    		        "/signup",
    		        "/css/**",
    		        "/js/**",
    		        "/images/**"
    		    ).permitAll()
    		    .anyRequest().authenticated()
    		)

            // ログイン設定
            .formLogin(form -> form
        	    .loginPage("/loginOnSession")
        	    .loginProcessingUrl("/doLogin")
        	    .defaultSuccessUrl("/", true)
        	    .failureUrl("/loginOnSession?error")
        	    .permitAll()
        	)

            // ログアウト設定
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
            );

        return http.build();
    }

    // パスワード暗号化
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
