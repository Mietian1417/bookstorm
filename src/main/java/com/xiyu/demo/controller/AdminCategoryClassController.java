package com.xiyu.demo.controller;

import com.xiyu.demo.pojo.Category;
import com.xiyu.demo.pojo.CategoryClass;
import com.xiyu.demo.result.ErrorCode;
import com.xiyu.demo.result.Result;
import com.xiyu.demo.service.CategoryClassService;
import com.xiyu.demo.service.CategoryService;
import com.xiyu.demo.vo.admin.EditCategoryClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-23
 * Time: 16:40
 *
 * @author 陈子豪
 */
@Controller
@RequestMapping("/admin")
@ResponseBody
public class AdminCategoryClassController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryClassService categoryClassService;

    /**
     * 修改二级分类 页面初始化数据
     *
     * @param categoryClassId
     * @return
     */
    @RequestMapping("/editCategoryClass")
    public Result<EditCategoryClassVo> editCategoryClass(Integer categoryClassId) {
        List<Category> categories = categoryService.list();
        CategoryClass categoryClass = categoryClassService.get(categoryClassId);
        if (categoryClass == null) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        EditCategoryClassVo categoryClassVo = new EditCategoryClassVo();
        categoryClassVo.setCategories(categories);
        categoryClassVo.setCategoryClass(categoryClass);
        return Result.success(categoryClassVo);
    }

    /**
     * 增加二级分类
     *
     * @param categoryClass
     * @return
     */
    @RequestMapping("/addCategoryClass")
    public Result<String> addCategoryClass(CategoryClass categoryClass) {
        int ret = categoryClassService.add(categoryClass);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("增加二级分类成功! ");
    }

    /**
     * 修改二级分类
     *
     * @param categoryClass
     * @return
     */
    @RequestMapping("/updateCategoryClass")
    public Result<String> update(CategoryClass categoryClass) {
        int ret = categoryClassService.update(categoryClass);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("修改二级分类成功! ");
    }

    /**
     * 删除二级分类
     *
     * @param categoryClassId
     * @return
     */
    @RequestMapping("/deleteCategoryClass")
    public Result<String> deleteCategory(Integer categoryClassId) {
        int ret = categoryClassService.delete(categoryClassId);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("删除二级分类成功! ");
    }

    /**
     * 获取所有二级分类(新增图书用到)
     * @return
     */
    @RequestMapping("/getAllCategoryClasses")
    public Result<List<CategoryClass>> getAllCategoryClasses() {
        List<CategoryClass> categoryClasses = categoryClassService.selectAll();
        return Result.success(categoryClasses);
    }
}
