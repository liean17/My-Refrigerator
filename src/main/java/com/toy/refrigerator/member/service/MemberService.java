package com.toy.refrigerator.member.service;

import com.toy.refrigerator.member.dto.MemberDto;
import com.toy.refrigerator.member.entity.Member;
import com.toy.refrigerator.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberDto.Response signup(MemberDto.Signup postDto) {
        Member member = dtoToEntity(postDto);
        memberRepository.save(member);
        return null;
    }

    public void login(MemberDto.Login postDto) {
        System.out.println("###########로그인 서비스 동작###########");
        //Member byEmail = memberRepository.findByEmail(postDto.getUsername());
        System.out.println("Username = " + postDto.getUsername());
        System.out.println("Password = " + postDto.getPassword());
        System.out.println("###########로그인  서비스  끝###########");
        //return byEmail;
    }

    private Member dtoToEntity(MemberDto.Signup postDto) {
        String encodePassword = passwordEncoder.encode(postDto.getPassword());
        return Member.builder()
                .email(postDto.getEmail())
                .password(encodePassword)
                .nickname(postDto.getNickname())
                .build();
    }
}
