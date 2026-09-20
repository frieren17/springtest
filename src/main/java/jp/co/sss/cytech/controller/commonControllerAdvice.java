package jp.co.sss.cytech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.sss.cytech.entity.Category;
import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.repository.CategoryRepository;
import jp.co.sss.cytech.service.LoginUserDetails;

@ControllerAdvice
public class commonControllerAdvice {

    @Autowired
    CategoryRepository categoryRepository;


    @ModelAttribute("categories")
    public List<Category> categories() {

        return categoryRepository.findAll();

    }


    @ModelAttribute("loginUser")
    public User loginUser(Authentication authentication) {

        if (authentication == null ||
            authentication.getPrincipal() instanceof String) {

            return null;
        }

        LoginUserDetails loginUserDetails =
                (LoginUserDetails) authentication.getPrincipal();

        return loginUserDetails.getUser();
    }
}