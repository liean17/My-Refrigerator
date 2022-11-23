package com.toy.refrigerator.auth.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.toy.refrigerator.auth.principal.PrincipalDetails;
import com.toy.refrigerator.member.entity.Member;
import com.toy.refrigerator.member.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberRepository memberRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소가 요청되었습니다.");
        String jwtHeader = request.getHeader("Authorization");

        if(jwtHeader==null||!jwtHeader.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }
        String jwtToken = jwtHeader.replace("Bearer ", "");

        String email = JWT.require(Algorithm.HMAC512("secret")).build().verify(jwtToken).getClaim("email").asString();

        if(email != null){
            System.out.println("정상 토큰 확인");
            Member member = memberRepository.findByEmail(email);
            PrincipalDetails principalDetails = new PrincipalDetails(member);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            System.out.println("유저 객체 저장 완료");

            chain.doFilter(request,response);
        }
    }
}
