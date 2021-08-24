package life.yihe.community.community.service;

import life.yihe.community.community.dto.PaginationDTO;
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

    public PaginationDTO list(Integer page, Integer size) {
        Integer totalPage;
        Integer totalCount = questionMapper.count();
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        if(page < 1)page = 1;
        if(page > totalPage)page = totalPage;
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO>questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for(Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//从前面的对象复制到后面的对象
            questionDTO.setUser(user);
            questionDTO.setDescription("abc");
//            System.out.println(questionDTO.getDescription());
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(totalPage,page,size);
        return paginationDTO;
    }
}

