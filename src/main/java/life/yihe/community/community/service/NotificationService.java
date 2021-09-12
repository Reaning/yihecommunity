package life.yihe.community.community.service;

import life.yihe.community.community.dto.NotificationDTO;
import life.yihe.community.community.dto.PaginationDTO;
import life.yihe.community.community.enums.NotificationStatusEnum;
import life.yihe.community.community.enums.NotificationTypeEnum;
import life.yihe.community.community.exception.CustomizeErrorCode;
import life.yihe.community.community.exception.CustomizeException;
import life.yihe.community.community.mapper.NotificationMapper;
import life.yihe.community.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO list(Long id, Integer page, Integer size) {
        Integer totalPage;
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                        .andReceiverEqualTo(id);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        if(page < 1)page = 1;
        if(page > totalPage)page = totalPage;
        Integer offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(id);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<NotificationDTO>notificationDTOS = new ArrayList<>();
        for(Notification notification:notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.getMessageByType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        PaginationDTO<NotificationDTO>paginationDTO = new PaginationDTO<>();
        paginationDTO.setData(notificationDTOS);
        paginationDTO.setPagination(totalPage,page,size);
        return paginationDTO;
    }
    public Integer getNewRepliesCount(Long id){
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(id)
                .andStatusEqualTo(NotificationStatusEnum.NEW.getStatus());
        Integer count = (int)notificationMapper.countByExample(example);
        return count;
    }

    @Transactional
    public Notification read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!notification.getReceiver().equals(user.getId())){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_READ_FAILED);
        }
        Notification record = new Notification();
        record.setId(id);
        record.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(record);
        return notification;
    }
}
