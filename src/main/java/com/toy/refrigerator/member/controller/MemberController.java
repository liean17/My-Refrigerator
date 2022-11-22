package com.toy.refrigerator.member.controller;

import com.toy.refrigerator.member.dto.MemberDto;
import com.toy.refrigerator.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //Join
    @GetMapping("/signup")
    public String joinPage(@ModelAttribute("member") MemberDto.Signup memberSignupDto){
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(MemberDto.Signup postDto, Model model){
         MemberDto.Response response = memberService.signup(postDto);
         model.addAttribute("member",response);
         return "redirect:/sectors";
    }

    //Login
    @GetMapping("/login")
    public String loginPage(){
        return "member/login";
    }

//    @PostMapping("/login")
//    public String login(MemberDto.Login postDto, Model model){
//        //MemberDto.Response response = memberService.login(postDto);
//        System.out.println("로그인 로직 동작");
//        return "redirect:/sectors";
//    }

    //get,patch

    //delete
}
