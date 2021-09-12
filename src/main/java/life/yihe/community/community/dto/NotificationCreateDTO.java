package life.yihe.community.community.dto;

import life.yihe.community.community.enums.NotificationTypeEnum;
import life.yihe.community.community.model.Comment;
import lombok.Data;

@Data
public class NotificationCreateDTO {
    private Comment comment;
    private Long receiverId;
    private Long outerId;
    private String outerTitle;
    private String notifierName;
    private NotificationTypeEnum notificationTypeEnum;

    public NotificationCreateDTO(Comment comment, Long receiverId, Long outerId, String outerTitle, String notifierName, NotificationTypeEnum notificationTypeEnum) {
        this.comment = comment;
        this.receiverId = receiverId;
        this.outerId = outerId;
        this.outerTitle = outerTitle;
        this.notifierName = notifierName;
        this.notificationTypeEnum = notificationTypeEnum;
    }
}
