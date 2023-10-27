package com.xiyu.demo.service;


import com.xiyu.demo.pojo.Category;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 10:55
 *
 * @author 陈子豪
 */
public interface CategoryService {

    /**
     * 返回分类列表
     *
     * @return
     */
    List<Category> list();


    /**
     * 通过id获取对应的数据
     *
     * @param id
     * @return
     */
    Category get(Integer id);

    /**
     * 更新分类
     *
     * @param category
     * @return
     */
    int update(Category category);

    int add(Category category);

    int delete(Integer id);

}
