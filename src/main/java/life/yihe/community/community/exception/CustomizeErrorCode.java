package life.yihe.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTIOM_NOT_FOUND ("你找的问题不在了，换一个试试？");

    public String getMessage(){
        return this.message;
    }
    private String message;
    CustomizeErrorCode(String message) {
        this.message = message;
    }

}
