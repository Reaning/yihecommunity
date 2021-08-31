package life.yihe.community.community.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface QuestionExtMapper {
    @Update("update question set view_count = view_count + 1 where id = #{id}")
    void incView(@Param("id") Integer id);
}
