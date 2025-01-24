package com.example.board.common.service;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // spring security filter 설정을 customizing 하기 위한 어노테이션
//      (true) pre: 사전 검증(컨트롤러 가기 전), (false) post: 사후 검증(컨트롤러 갔다 나오면서)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception { //        예외처리 예쁘게 해야됨
        return httpSecurity
                //      csrf공격에 대한 설정은 하지 않겠다는 의미
                .csrf().disable()
                //      어떤 화면은 허락하고 어떤 화면은 허락하지 않는다는 의미
                .authorizeRequests().antMatchers("/","/author/create","/author/login").permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/author/login")
                            //      사전에 구현되어있는 doLogind 메서드를 그대로 사용,
                            .loginProcessingUrl("/doLogin")
                            //      다만, doLogin에 넘겨줄 email, password의 변수명은 별도로 지정해야함
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .successHandler(new LoginSuccessHandler())
                .and()
                    //      spring security 에 사전 구현되어있는 doLogout 메서드 그대로 사용.
                    .logout().logoutUrl("/doLogout")
                .and()
                .build();
    }

    @Bean
    //      오토와이어드 해야하는 이유: 직접 써야하기 때문에 쓰는 쪽에서 autowired 해야함.
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
