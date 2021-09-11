package life.yihe.community.community.controller;

import life.yihe.community.community.dto.CommentDTO;
import life.yihe.community.community.dto.QuestionDTO;
import life.yihe.community.community.enums.CommentTypeEnum;
import life.yihe.community.community.model.Question;
import life.yihe.community.community.service.CommentService;
import life.yihe.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model
                           ){
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.QUESTION.getType());
        List<Question>relatedQuestions = questionService.selectRelated(questionDTO);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTOList);
        model.addAttribute("relatedQuestions",relatedQuestions);
        questionService.incView(id);
        return "question";
    }
}
