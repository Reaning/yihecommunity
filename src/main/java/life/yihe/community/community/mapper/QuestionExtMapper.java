package life.yihe.community.community.mapper;


import life.yihe.community.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface QuestionExtMapper {
    @Update("update question set view_count = view_count + 1 where id = #{id}")
    void incView(@Param("id") Long id);
    @Update("update question set comment_count = comment_count + 1 where id = #{id}")
    void incCommentCount(@Param("id") Long id);
    @Select("select * from question where id != #{id} and tag regexp #{tag}")
    List<Question> relatedQuestion(@Param("id")Long id,@Param("tag") String regexpTag);
}
