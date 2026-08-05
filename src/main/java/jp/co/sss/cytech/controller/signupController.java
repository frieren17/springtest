package jp.co.sss.cytech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.cytech.dto.UserRequest;
import jp.co.sss.cytech.service.UserService;

@Controller
public class signupController {

    private final UserService userService;

    public signupController(UserService userService) {
        this.userService = userService;
    }

    // 画面表示
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "signUp";
    }

    // 登録処理
    @PostMapping("/signup")
    public String register(UserRequest request, Model model) {

        // ① パスワード確認チェック
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            model.addAttribute("error", "パスワードが一致しません");
            return "signUp";
        }

        // ② 登録処理
        userService.register(request);

        // ③ 完了後ログイン画面へ
        return "redirect:/loginOnSession";
    }
}