package life.yihe.community.community.controller;

import life.yihe.community.community.dto.CommentDTO;
import life.yihe.community.community.mapper.CommentMapper;
import life.yihe.community.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;

//    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(1);
        commentMapper.insert(comment);
        return null;
    }
}
