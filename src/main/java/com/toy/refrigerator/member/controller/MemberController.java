package com.toy.refrigerator.member.controller;

import com.toy.refrigerator.member.dto.MemberDto;
import com.toy.refrigerator.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //Join
    @GetMapping("/signup")
    public String joinPage(){
        return "member/signup";
    }
    @PostMapping("/signup")
    public String signup(MemberDto.Post postDto, Model model){
         MemberDto.Response response = memberService.signup(postDto);
         model.addAttribute("member",response);
         return "redirect:/sectors";
    }

    //Login

    //get,patch

    //delete
}
