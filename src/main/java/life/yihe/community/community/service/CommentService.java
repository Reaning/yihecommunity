package life.yihe.community.community.service;

import life.yihe.community.community.dto.CommentDTO;
import life.yihe.community.community.dto.NotificationCreateDTO;
import life.yihe.community.community.enums.CommentTypeEnum;
import life.yihe.community.community.enums.NotificationStatusEnum;
import life.yihe.community.community.enums.NotificationTypeEnum;
import life.yihe.community.community.exception.CustomizeErrorCode;
import life.yihe.community.community.exception.CustomizeException;
import life.yihe.community.community.mapper.*;
import life.yihe.community.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Receiver;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment,User user) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_NOT_FOUND);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            NotificationCreateDTO notificationCreateDTO = new NotificationCreateDTO(
                    comment,
                    dbComment.getCommentator(),
                    dbComment.getParentId(),
                    question.getTitle(),
                    user.getName(),
                    NotificationTypeEnum.REPLY_COMMENT
            );
            addNotificattion(notificationCreateDTO);
            commentExtMapper.incCommentCount(comment.getParentId());
        }else{
            //回复问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(dbQuestion == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTIOM_NOT_FOUND);
            }
            commentMapper.insert(comment);
            NotificationCreateDTO notificationCreateDTO = new NotificationCreateDTO(
                    comment,
                    dbQuestion.getCreator(),
                    dbQuestion.getId(),
                    dbQuestion.getTitle(),
                    user.getName(),
                    NotificationTypeEnum.REPLY_QUESTION
            );
            addNotificattion(notificationCreateDTO);
            questionExtMapper.incCommentCount(comment.getParentId());
        }
    }
    public void addNotificattion(NotificationCreateDTO notificationCreateDTO){
        Notification notification = new Notification();
        Comment comment = notificationCreateDTO.getComment();
        notification.setOuterid(notificationCreateDTO.getOuterId());
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(notificationCreateDTO.getReceiverId());
        notification.setNotifierName(notificationCreateDTO.getNotifierName());
        notification.setOuterTitle(notificationCreateDTO.getOuterTitle());
        notification.setGmtCreate(comment.getGmtCreate());
        notification.setStatus(NotificationStatusEnum.NEW.getStatus());
        notification.setType(notificationCreateDTO.getNotificationTypeEnum().getType());
        notificationMapper.insert(notification);
    }
    public List<CommentDTO> listByTargetId(Long id,Integer type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type);
        commentExample.setOrderByClause("gmt_create asc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size() == 0){
            return new ArrayList<>(0);
        }
        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long>userIds = new ArrayList<>();
        userIds.addAll(commentators);
        //获取评论人并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long,User>userMap = users.stream().collect(Collectors.toMap(user->user.getId(),user -> user));
        //转换comment为commentDTO
        List<CommentDTO>commentDTOList = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOList;
    }
}
