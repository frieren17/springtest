package jp.co.sss.cytech.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.sss.cytech.service.LoginUserDetails;

@Controller
public class mypageController {

    @GetMapping("/mypage")
    public String mypage(
            @AuthenticationPrincipal LoginUserDetails loginUser,
            Model model) {

        model.addAttribute("user", loginUser.getUser());

        return "mypage";
    }
}