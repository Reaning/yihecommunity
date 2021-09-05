package life.yihe.community.community.dto;

import lombok.Data;

@Data
public class GiteeDTO {
    private String grant_type;
    private String code;
    private String client_id;
    private String redirect_uri;
    private String client_secret;
}
