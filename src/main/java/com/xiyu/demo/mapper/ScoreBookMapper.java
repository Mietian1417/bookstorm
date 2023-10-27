package com.xiyu.demo.mapper;

import com.xiyu.demo.pojo.ScoreBook;
import com.xiyu.demo.pojo.ScoreBookExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-09-20
 * Time: 12:53
 *
 * @author 陈子豪
 */
public interface ScoreBookMapper {
    long countByExample(ScoreBookExample example);

    int deleteByExample(ScoreBookExample example);

    int insert(ScoreBook record);

    int insertSelective(ScoreBook record);

    List<ScoreBook> selectByExample(ScoreBookExample example);

    int updateByExampleSelective(@Param("record") ScoreBook record, @Param("example") ScoreBookExample example);

    int updateByExample(@Param("record") ScoreBook record, @Param("example") ScoreBookExample example);
}