package life.yihe.community.community.mapper;

//import life.majiang.community.model.User;
import life.yihe.community.community.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by codedrinker on 2019/4/30.
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token} ")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id=#{accountid}")
    User findByAccountId(@Param("accountid") String accountId);
    @Update("update user set name = #{name}, token = #{token},avatar_url = #{avatarUrl}, gmt_modified = #{gmtModified} where id = #{id}")
    void update(User dbUser);
}