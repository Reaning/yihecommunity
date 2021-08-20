package life.yihe.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HelloController {
    @GetMapping("/")
//    @ResponseBody
    public String hello(){
        return "index";
    }
}
