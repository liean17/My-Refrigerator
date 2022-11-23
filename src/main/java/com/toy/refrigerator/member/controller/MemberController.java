package com.toy.refrigerator.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.refrigerator.member.dto.MemberDto;
import com.toy.refrigerator.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;

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
         return "redirect:/";
    }

    //Login
    @GetMapping("/login")
    public String loginPage(){
        return "member/login";
    }

//    @PostMapping("/login")
//    public String login(MemberDto.Login postDto, RedirectAttributes redirectAttributes, Model model) throws IOException {
//        memberService.login(postDto);
//        return "redirect:/sectors";
//    }

    //get,patch

    //delete
}
