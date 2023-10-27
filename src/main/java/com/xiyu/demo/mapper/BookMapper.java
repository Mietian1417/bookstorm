package com.xiyu.demo.mapper;

import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.BookExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-09-20
 * Time: 16:20
 *
 * @author 陈子豪
 */
public interface BookMapper {
    long countByExample(BookExample example);

    int deleteByExample(BookExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Book record);

    int insertSelective(Book record);

    List<Book> selectByExample(BookExample example);

    Book selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Book record, @Param("example") BookExample example);

    int updateByExample(@Param("record") Book record, @Param("example") BookExample example);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
}