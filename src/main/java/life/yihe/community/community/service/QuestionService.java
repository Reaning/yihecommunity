package life.yihe.community.community.service;

import life.yihe.community.community.dto.PaginationDTO;
import life.yihe.community.community.dto.QuestionDTO;
import life.yihe.community.community.exception.CustomizeErrorCode;
import life.yihe.community.community.exception.CustomizeException;
import life.yihe.community.community.exception.ICustomizeErrorCode;
import life.yihe.community.community.mapper.QuestionExtMapper;
import life.yihe.community.community.mapper.QuestionMapper;
import life.yihe.community.community.mapper.UserMapper;
import life.yihe.community.community.model.Question;
import life.yihe.community.community.model.QuestionExample;
import life.yihe.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer totalPage;
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        if(page < 1)page = 1;
        if(page > totalPage)page = totalPage;
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO>questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for(Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//从前面的对象复制到后面的对象
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(totalPage,page,size);
        return paginationDTO;
    }
    public PaginationDTO list(Long userId, Integer page,Integer size){
        Integer totalPage;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                        .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        if(page < 1)page = 1;
        if(page > totalPage)page = totalPage;
        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO>questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for(Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//从前面的对象复制到后面的对象
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(totalPage,page,size);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTIOM_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));
        return questionDTO;
    }
    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            Question record = new Question();
            record.setGmtModified(System.currentTimeMillis());
            record.setTitle(question.getTitle());
            record.setDescription(question.getDescription());
            record.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                            .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(record, example);
        }
    }

    public void incView(Long id) {
//        Question question = questionMapper.selectByPrimaryKey(id);
//        Question updateQuestion = new Question();
//        updateQuestion.setViewCount(question.getViewCount() + 1);
//        QuestionExample questionExample = new QuestionExample();
//        questionExample.createCriteria()
//                        .andIdEqualTo(id);
//        questionMapper.updateByExampleSelective(updateQuestion, questionExample);
        questionExtMapper.incView(id);
    }
}

