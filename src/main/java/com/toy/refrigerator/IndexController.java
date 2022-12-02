package com.toy.refrigerator;

import com.toy.refrigerator.auth.principal.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String goHome(@AuthenticationPrincipal PrincipalDetails principalDetails){
        if (principalDetails==null){
            return "index";
        }
        return "redirect:/sectors";
    }
}
