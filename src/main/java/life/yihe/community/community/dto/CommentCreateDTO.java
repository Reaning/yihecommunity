package life.yihe.community.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long parentId;
    private int type;
    private String content;
}
