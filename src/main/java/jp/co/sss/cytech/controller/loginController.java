/*package jp.co.sss.cytech.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.repository.UserRepository;

@Controller
public class loginController {
	@Autowired
    private UserRepository userRepository;
	
	@PostMapping("/doLoginOnSession")
	public String doLoginOnSession(@RequestParam String email,
	                               @RequestParam String password,
	                               HttpSession session) {

	    User user = userRepository.findByEmail(email);

	    if (user == null || !user.getPassword().equals(password)) {
	        return "loginOnSession";
	    }

	    session.setAttribute("userId", user.getId());

	    return "redirect:/";
	}
}*/
