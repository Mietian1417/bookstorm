package com.xiyu.demo.service.impl;

import com.xiyu.demo.mapper.CategoryMapper;
import com.xiyu.demo.pojo.Category;
import com.xiyu.demo.pojo.CategoryExample;
import com.xiyu.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 11:12
 *
 * @author 陈子豪
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        CategoryExample example = new CategoryExample();
        return categoryMapper.selectByExample(example);
    }

    @Override
    public Category get(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Category category) {
        return categoryMapper.updateByPrimaryKey(category);
    }

    @Override
    public int add(Category category) {
        int i = categoryMapper.insertSelective(category);
        return i;
    }

    @Override
    public int delete(Integer id) {
        int i = categoryMapper.deleteByPrimaryKey(id);
        return id;
    }


}
