package org.choongang.configs;

import org.choongang.member.service.LoginFailureHandler;
import org.choongang.member.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //시큐리티의 핵심 기능 1.인증(로그인) 2.인가(페이지 접근?)
        //시큐리티가 해주지 못하는 부분만 우리가 직접 설정해주기
        /* 인증 설정 S - 로그인 */
        http.formLogin(f -> {
            f.loginPage("/member/login") //달라질 수 있는 부분 넣기
                    .usernameParameter("username")
                    .passwordParameter("password")
                    //단순 이동이 아닌
                    //상세한 설정을 위해 handler 이용
                    .successHandler(new LoginSuccessHandler()) //성공시 이동할 페이지
                    .failureHandler(new LoginFailureHandler()); //실패시 이동할 페이지
        }); //도메인 특화언어 방식
        /* 인증 설정 E - 로그인 */


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
