package com.toy.refrigerator.auth.principal;

import com.toy.refrigerator.member.entity.Member;
import com.toy.refrigerator.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("########################");
        System.out.println("loadUserByUsername 실행!!");
        System.out.println("username = " + username);
        System.out.println("########################");

        Member member = memberRepository.findByEmail(username).orElseThrow();

        return new PrincipalDetails(member);
    }
}
