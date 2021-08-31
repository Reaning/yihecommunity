package life.yihe.community.community.service;

import life.yihe.community.community.mapper.UserMapper;
import life.yihe.community.community.model.User;
import life.yihe.community.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample example = new UserExample();
        System.out.print(user.getId());
        example.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(example);
        if(dbUsers.size() == 0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            User dbUser = dbUsers.get(0);
            User update = new User();
            update.setGmtModified(System.currentTimeMillis());
            update.setAvatarUrl(user.getAvatarUrl());
            update.setName(user.getName());
            update.setToken(user.getToken());
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                            .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(update, userExample);
        }
    }
}
