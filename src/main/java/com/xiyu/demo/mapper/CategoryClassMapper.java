package com.xiyu.demo.mapper;

import com.xiyu.demo.pojo.CategoryClass;
import com.xiyu.demo.pojo.CategoryClassExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 书籍分类的二级分类
 */
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-09-20
 * Time: 20:30
 *
 * @author 陈子豪
 */
public interface CategoryClassMapper {
    long countByExample(CategoryClassExample example);

    int deleteByExample(CategoryClassExample example);

    int insert(CategoryClass record);

    int insertSelective(CategoryClass record);

    CategoryClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(CategoryClass record);

    List<CategoryClass> selectByCategoryId(Integer id);

    List<CategoryClass> selectByExample(CategoryClassExample example);

    int updateByExampleSelective(@Param("record") CategoryClass record, @Param("example") CategoryClassExample example);

    int updateByExample(@Param("record") CategoryClass record, @Param("example") CategoryClassExample example);
}