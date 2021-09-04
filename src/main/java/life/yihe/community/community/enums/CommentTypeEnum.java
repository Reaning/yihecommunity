package life.yihe.community.community.enums;

import java.util.Objects;

public enum CommentTypeEnum {
    QUESTION(1),COMMENT(2);
    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for(CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()){
            if(Objects.equals(commentTypeEnum.getType(), type))return true;
        }
        return false;
    }
}
