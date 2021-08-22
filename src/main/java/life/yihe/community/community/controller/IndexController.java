package life.yihe.community.community.controller;

import life.yihe.community.community.mapper.UserMapper;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class IndexController {
    @GetMapping("/")
//    @ResponseBody
    public String hello(){

        return "index";
    }
}
