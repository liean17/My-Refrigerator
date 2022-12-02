package com.toy.refrigerator.auth.config;

import com.toy.refrigerator.auth.filter.JwtAuthenticationFilter;
import com.toy.refrigerator.auth.filter.JwtAuthorizationFilter;
import com.toy.refrigerator.auth.principal.PrincipalDetailsService;
import com.toy.refrigerator.member.repository.MemberRepository;
import com.toy.refrigerator.member.service.MemberService;
import com.toy.refrigerator.oauth.Oauth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberRepository memberRepository;
    private final Oauth2Service oauth2Service;

    @Bean
    public BCryptPasswordEncoder encoder(){return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.headers().frameOptions().disable();
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers("/sectors/**","/foods/**").authenticated()
                //.antMatchers("/").not().authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/sectors")
                .and()
                .httpBasic().disable()
                //.apply(new CustomDsl())
                //.and()
                .exceptionHandling();
        http
                .logout()
                .logoutUrl("/logout");
        http
                .oauth2Login()
                .loginPage("/oauth2/authorization/google")
                .defaultSuccessUrl("/sectors")
                .userInfoEndpoint()
                .userService(oauth2Service);
        return http.build();
    }

    public class CustomDsl extends AbstractHttpConfigurer<CustomDsl,HttpSecurity>{

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager,memberRepository));
        }
    }
}
