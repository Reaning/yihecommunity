package life.yihe.community.community.dto;

import life.yihe.community.community.exception.CustomizeException;
import life.yihe.community.community.exception.ICustomizeErrorCode;
import lombok.Data;

import javax.xml.transform.Result;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(ICustomizeErrorCode errorCode){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(errorCode.getCode());
        resultDTO.setMessage(errorCode.getMessage());
        return resultDTO;
    }
    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException ex) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ex.getMessage());
        resultDTO.setCode(ex.getCode());
        return resultDTO;
    }
    public static<T> ResultDTO okOf(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
