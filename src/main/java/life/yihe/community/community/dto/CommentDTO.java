package life.yihe.community.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long parentId;
    private int type;
    private String content;
}
