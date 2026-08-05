/*package jp.co.sss.cytech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.cytech.dto.UserRequest;
import jp.co.sss.cytech.service.UserService;

@Controller
public class userController {
	@Autowired
    private UserService userService;

    public String signup(Model model) {

        model.addAttribute(
            "userRequest",
            new UserRequest());

        return "signup";
    }

    @PostMapping("/signup")
    public String register(
            @ModelAttribute UserRequest userRequest) {

        if (!userRequest.getPassword()
                .equals(userRequest.getConfirmPassword())) {

            return "signup";
        }

        userService.register(userRequest);

        return "redirect:/login";
    }
}*/
