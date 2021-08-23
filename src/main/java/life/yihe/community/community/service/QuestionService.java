package life.yihe.community.community.service;

import life.yihe.community.community.dto.QuestionDTO;
import life.yihe.community.community.mapper.QuestionMapper;
import life.yihe.community.community.mapper.UserMapper;
import life.yihe.community.community.model.Question;
import life.yihe.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> list() {
        List<Question>questions = questionMapper.list();
        List<QuestionDTO>questionDTOList = new ArrayList<>();
        for(Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//从前面的对象复制到后面的对象
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
