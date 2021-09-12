package life.yihe.community.community.controller;

import life.yihe.community.community.dto.NotificationDTO;
import life.yihe.community.community.enums.NotificationTypeEnum;
import life.yihe.community.community.model.Notification;
import life.yihe.community.community.model.User;
import life.yihe.community.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        Notification notification = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notification.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notification.getType()) {
            return "redirect:/question/" + notification.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
