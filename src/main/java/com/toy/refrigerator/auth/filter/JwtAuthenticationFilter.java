package com.toy.refrigerator.auth.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.refrigerator.auth.principal.PrincipalDetails;
import com.toy.refrigerator.member.dto.MemberDto;
import com.toy.refrigerator.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl("/logins");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("로그인 시도 중");
        try {
            ObjectMapper om = new ObjectMapper();
            //TODO member 정보를 가져오지 못함, 아니 애초에 어떻게 가져오는가?
            Member member = om.readValue(request.getInputStream(),Member.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 완료");
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("secret")
                .withExpiresAt(new Date(System.currentTimeMillis() + (360 * 1000 * 10)))
                .withClaim("id", principal.getMember().getId())
                .withClaim("email", principal.getMember().getEmail())
                .sign(Algorithm.HMAC512("secret"));

        log.info("쿠키 생성");
        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setMaxAge(1000*60*60*24*7);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }
}
