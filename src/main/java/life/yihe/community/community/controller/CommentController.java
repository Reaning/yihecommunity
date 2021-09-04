package life.yihe.community.community.controller;

import life.yihe.community.community.dto.CommentDTO;
import life.yihe.community.community.dto.ResultDTO;
import life.yihe.community.community.exception.CustomizeErrorCode;
import life.yihe.community.community.exception.CustomizeException;
import life.yihe.community.community.mapper.CommentMapper;
import life.yihe.community.community.model.Comment;
import life.yihe.community.community.model.User;
import life.yihe.community.community.service.CommentService;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

//    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    @PostMapping("/comment")

    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request
    ){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
//            ResultDTO resultDTO = ResultDTO.errorOf(2002,"还未进行登录，请先登录后再提交！");
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
