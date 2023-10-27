package com.xiyu.demo.mapper;

import com.xiyu.demo.pojo.Category;
import com.xiyu.demo.pojo.CategoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 书籍分类的一级分类
 */
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-09-20
 * Time: 21:28
 *
 * @author 陈子豪
 */
public interface CategoryMapper {
    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}