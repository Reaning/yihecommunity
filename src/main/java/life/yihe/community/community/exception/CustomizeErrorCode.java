package life.yihe.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTIOM_NOT_FOUND (2001,"你找的问题不在了，换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题和评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试！"),
    SYS_ERROR(2004,"服务冒烟了，请稍后再试！"),
    TYPE_PARAM_NOT_FOUND(2005,"评论类型不存在或错误"),
    COMMENT_NOT_FOUND(2006,"你找的评论不在了，换一个试试？");

    public String getMessage(){
        return this.message;
    }

    public Integer getCode(){
        return this.code;
    }

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

}
