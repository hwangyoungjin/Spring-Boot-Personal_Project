package my.springboot.myrest.controller;

import my.springboot.myrest.model.User;
import my.springboot.myrest.repository.UserRepository;
import my.springboot.myrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    @PostMapping("/register")
    public String register(User user){
        userService.save(user);
        return "redirect:/"; //가입 완료시 바로 로그인되어 home(index.html)으로 이동
    }


    @GetMapping("/register")
    public String register(){
        return "account/register";
    }
}
