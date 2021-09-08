package life.yihe.community.community.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface CommentExtMapper {
    @Update("update comment set comment_count = comment_count + 1 where id = #{id}")
    void incCommentCount(@Param("id")Long id);
}