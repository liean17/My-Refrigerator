package com.toy.refrigerator.auth.config;

import com.toy.refrigerator.auth.filter.JwtAuthenticationFilter;
import com.toy.refrigerator.auth.filter.JwtAuthorizationFilter;
import com.toy.refrigerator.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberRepository memberRepository;

    @Bean
    public BCryptPasswordEncoder encoder(){return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin().disable()
//                .loginPage("/login")
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/sectors")
//                .and()
                .httpBasic().disable()
                .apply(new CustomDsl())
                .and()
                .exceptionHandling();

        return http.build();
    }

    public class CustomDsl extends AbstractHttpConfigurer<CustomDsl,HttpSecurity>{

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            /**TODO 설정하면 페이지가 안불러와짐
            jwtAuthenticationFilter.setFilterProcessesUrl("/login");*/
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager,memberRepository));
        }
    }
}
