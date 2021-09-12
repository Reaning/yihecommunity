package life.yihe.community.community.controller;

import life.yihe.community.community.dto.PaginationDTO;
import life.yihe.community.community.dto.QuestionDTO;
import life.yihe.community.community.model.Notification;
import life.yihe.community.community.model.User;
import life.yihe.community.community.service.NotificationService;
import life.yihe.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          Model model){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        PaginationDTO paginationDTO = null;
        if(action.equals("question")){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的问题");
            paginationDTO = questionService.list(user.getId(),page,size);
        }
        if(action.equals("replies")){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            paginationDTO = notificationService.list(user.getId(),page,size);
        }
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
