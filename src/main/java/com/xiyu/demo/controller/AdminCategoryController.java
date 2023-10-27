package com.xiyu.demo.controller;


import com.xiyu.demo.pojo.Category;
import com.xiyu.demo.pojo.CategoryClass;
import com.xiyu.demo.result.ErrorCode;
import com.xiyu.demo.result.Result;
import com.xiyu.demo.service.BookService;
import com.xiyu.demo.service.CategoryClassService;
import com.xiyu.demo.service.CategoryService;
import com.xiyu.demo.vo.admin.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-23
 * Time: 18:17
 *
 * @author 陈子豪
 */
@Controller
@RequestMapping("/admin")
@ResponseBody
public class AdminCategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    BookService bookService;

    @Autowired
    CategoryClassService categoryClassService;

    /**
     * 所有分类展示 页面初始化
     * @return
     */
    @RequestMapping("/categoryList")
    public Result<CategoryVo> list() {
        List<Category> categories = categoryService.list();
        List<CategoryClass> categoriesClasses = categoryClassService.selectAll();
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setCategories(categories);
        categoryVo.setCategoriesClasses(categoriesClasses);
        return Result.success(categoryVo);
    }

    /**
     * 修改一级分类
     * @param category
     * @return
     */
    @RequestMapping("/updateCategory")
    public Result<String> updateCategory(Category category) {
        int ret = categoryService.update(category);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("修改一级分类成功! ");
    }

    /**
     * 增加一级分类
     * @param category
     * @return
     */
    @RequestMapping("/addCategory")
    public Result<String> addCategory(Category category) {
        int ret = categoryService.add(category);
        if (ret == 0){
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("增加一级分类成功! ");
    }

    /**
     * 删除一级分类
     * @param id
     * @return
     */
    @RequestMapping("/deleteCategory")
    public Result<String> deleteCategory(Integer id) {
        int ret = categoryService.delete(id);
        if (ret == 0){
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("删除一级分类成功! ");
    }
}
