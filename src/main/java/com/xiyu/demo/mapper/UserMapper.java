package com.xiyu.demo.mapper;

import com.xiyu.demo.pojo.User;
import com.xiyu.demo.pojo.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-09-20
 * Time: 14:23
 *
 * @author 陈子豪
 */
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);
}