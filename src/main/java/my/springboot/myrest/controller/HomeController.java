package my.springboot.myrest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping // 기본 홈 "/" 화면은 입력 안해도 된다.
    public String index(){
        return "index";
    }
}
