package org.choongang.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.choongang.member.service.LoginFailureHandler;
import org.choongang.member.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
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

        http.logout(c -> {
            //로그아웃 후 로그인 페이지로 이동
            //AntPathRequestMatcher: URL 패턴 매칭 클래스
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/member/login"); //로그아웃 성공시 이동 페이지
        });
        /* 인증 설정 E - 로그인, 로그아웃 */

        /* 인가 설정 S - 접근 통제 */
        //DB 기록 시
        //hasAuthority(..) hasAnyAutority(...)(여러개), hasRole, hasAnyRole
        //ROLE_롤명칭(롤명칭으로 직접 접근)
        //사용법: hasAuthority('ADMIN')
        //사용법: ROLE_ADMIN -> hasAuthority('ROLE_ADMIN')
        //사용법: hasRole('ADMIN')
        http.authorizeHttpRequests(c -> { //인가설정 (특정권한이 있을 때 접근가능한 페이지 통제)
            c.requestMatchers("/mypage/**").authenticated() //회원 전용(로그인시만 접근가능)
                    .requestMatchers("/admin/**") //
                    .hasAnyAuthority("ADMIN", "MANAGER") //이 권한이 있는 사람만 접근가능 (없으면 로그인 페이지로 이동)
                    .anyRequest().permitAll(); //그외 모든 페이지는 모두 접근 가능

        });
    /*
        http.exceptionHandling(c -> {
            c.authenticationEntryPoint(new AuthenticationEntryPoint() {
                @Override
                public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {

                }
            });
            }); //직접생성하는 방법


     */
        //위를 람다식으로 표현
        http.exceptionHandling(c -> {
            c.authenticationEntryPoint(( req, res, e) -> {
                String URL = req.getRequestURI();
                if(URL.indexOf("/admin") != -1 ) { //관리자 페이지
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                } else { //회원 전용 페이지
                    res.sendRedirect(req.getContextPath()+"/member/login");
                }

            });
        });

        /* 인가 설정 E - 접근 통제 */



        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
