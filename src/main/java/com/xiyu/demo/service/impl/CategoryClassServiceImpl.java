package com.xiyu.demo.service.impl;


import com.xiyu.demo.mapper.BookMapper;
import com.xiyu.demo.mapper.CategoryClassMapper;
import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.CategoryClass;
import com.xiyu.demo.pojo.CategoryClassExample;
import com.xiyu.demo.service.CategoryClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 10:15
 *
 * @author 陈子豪
 */
@Service
public class CategoryClassServiceImpl implements CategoryClassService {

    @Autowired
    CategoryClassMapper categoryClassMapper;

    @Autowired
    BookMapper bookMapper;

    @Override
    public List<CategoryClass> list(Integer category_id) {
        CategoryClassExample example = new CategoryClassExample();
        example.or().andCategoryIdEqualTo(category_id);
        return categoryClassMapper.selectByExample(example);
    }

    @Override
    public List<CategoryClass> selectByCategoryId(Integer category_id) {
        List<CategoryClass> classList = categoryClassMapper.selectByCategoryId(category_id);
        return classList;
    }

    @Override
    public CategoryClass get(Integer id) {
        return categoryClassMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(CategoryClass categoryClass) {
        return categoryClassMapper.updateByPrimaryKey(categoryClass);
    }

    @Override
    public int add(CategoryClass categoryClass) {
        return categoryClassMapper.insertSelective(categoryClass);
    }

    @Override
    public List<Book> getListBookByClass(Integer categoryId) {
        // List<Book> books = bookMapper.getListBookByClass(categoryId);
        // return books;
        List<Book> list = new ArrayList<>();
        return list;
    }

    @Override
    public void put(Integer id) {
        System.out.println("lalalaallal------------------------------" + id);
    }

    @Override
    public int delete(Integer id) {
        CategoryClassExample categoryClassExample = new CategoryClassExample();
        categoryClassExample.or().andIdEqualTo(id);
        int i = categoryClassMapper.deleteByExample(categoryClassExample);
        return i;
    }

    @Override
    public List<CategoryClass> selectAll() {
        CategoryClassExample categoryClassExample = new CategoryClassExample();
        return categoryClassMapper.selectByExample(categoryClassExample);
    }
}
