package com.xiyu.demo.vo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-09
 * Time: 17:17
 *
 * @author 陈子豪
 */

import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.Category;
import com.xiyu.demo.pojo.CategoryClass;
import lombok.Data;

import java.util.List;

/**
 * 主主页面返回的视图对象
 */
@Data
public class HomeDataVo {
    // 猜你喜欢书籍
    private List<Book> guessYouLikeBooks;
    // 新书速递书籍
    private List<Book> newBooks;
    // 热门书籍
    private List<Book> popularBooks;
    // 一级分类
    private List<Category> categories;
    // 二级分类
    private List<List<CategoryClass>> categoriesClasses;
}
