package life.yihe.community.community.controller;

import life.yihe.community.community.Provider.GiteeProvider;
import life.yihe.community.community.Provider.GithubProvider;
import life.yihe.community.community.dto.GiteeDTO;
import life.yihe.community.community.dto.UserDTO;
import life.yihe.community.community.model.User;
import life.yihe.community.community.service.QuestionService;
import life.yihe.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller

public class AuthorizeController {

    @Autowired //把Spring写好的实例化的实例加载到上下文
    private GithubProvider githubProvider;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private GiteeProvider giteeProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

//    @GetMapping("/callback")
//    public String callback(@RequestParam(name = "code")String code,
//                           @RequestParam(name = "state")String state,
//                           HttpServletRequest request,
//                           HttpServletResponse response){
//        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
//        accessTokenDTO.setClient_id(clientId);
//        accessTokenDTO.setClient_secret(clientSecret);
//        accessTokenDTO.setCode(code);
//        accessTokenDTO.setRedirect_uri(redirectUri);
//        accessTokenDTO.setState(state);
//        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
//        GithubUser githubUser = githubProvider.getUser(accessToken);
//        System.out.println(githubUser.getName());
//        if (githubUser.getId() != null) {
//            User user = new User();
//            String token = UUID.randomUUID().toString();
//            user.setToken(token);
//            user.setName(githubUser.getName());
//            user.setAccountId(String.valueOf(githubUser.getId()));
//            user.setAvatarUrl(githubUser.getAvatar_url());
//            userService.createOrUpdate(user);
//            response.addCookie(new Cookie("token",token));
//            // 登录成功，写cookie 和session
//            request.getSession().setAttribute("user", githubUser);
//            return "redirect:/";
//        } else {
//            // 登录失败，重新登录
//            return "redirect:/";
//        }
//    }
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
//                           @RequestParam(name = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        GiteeDTO giteeDTO = new GiteeDTO();
        giteeDTO.setGrant_type("authorization_code");
        giteeDTO.setClient_id(clientId);
        giteeDTO.setClient_secret(clientSecret);
        giteeDTO.setCode(code);
        giteeDTO.setRedirect_uri(redirectUri);
//        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        String accessToken = giteeProvider.getAccessToken(giteeDTO);
        UserDTO userDTO = giteeProvider.getUser(accessToken);
        System.out.println(userDTO.getName());
        if (userDTO.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(userDTO.getName());
            user.setAccountId(String.valueOf(userDTO.getId()));
            user.setAvatarUrl(userDTO.getAvatar_url());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            // 登录成功，写cookie 和session
            request.getSession().setAttribute("user", userDTO);
            return "redirect:/";
        } else {
            // 登录失败，重新登录
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }
}

