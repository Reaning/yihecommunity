package life.yihe.community.community.enums;

public enum NotificationStatusEnum {
    NEW(1,"新通知"),
    READ(2,"已读");
    private Integer status;
    private String message;

    NotificationStatusEnum(Integer status,String message){
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
