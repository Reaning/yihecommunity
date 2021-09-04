package life.yihe.community.community.advice;

import life.yihe.community.community.dto.ResultDTO;
import life.yihe.community.community.exception.CustomizeErrorCode;
import life.yihe.community.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    Object handle(HttpServletRequest request,Throwable ex, Model model) {
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            if (ex instanceof CustomizeException) {
                return ResultDTO.errorOf((CustomizeException)ex);
            } else {
                return ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
        }
        else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", "服务冒烟了，请稍后再试！");
            }
            return new ModelAndView("error");
        }
    }
}
