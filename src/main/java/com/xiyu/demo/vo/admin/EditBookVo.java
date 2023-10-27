package com.xiyu.demo.vo.admin;

import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.CategoryClass;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-23
 * Time: 21:47
 *
 * @author 陈子豪
 */

@Data
public class EditBookVo {
    private Book book;

    // 所有分类
    private List<CategoryClass> categoryClasses;

    // 当前分类
    private CategoryClass categoryClass;
}
