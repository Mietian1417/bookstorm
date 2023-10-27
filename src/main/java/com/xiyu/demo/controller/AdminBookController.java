package com.xiyu.demo.controller;

import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.CategoryClass;
import com.xiyu.demo.result.ErrorCode;
import com.xiyu.demo.result.Result;
import com.xiyu.demo.service.BookService;
import com.xiyu.demo.service.CategoryClassService;
import com.xiyu.demo.vo.admin.CategoryClassBookListVo;
import com.xiyu.demo.vo.admin.EditBookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-23
 * Time: 16:17
 *
 * @author 陈子豪
 */

@RequestMapping("/admin")
@ResponseBody
@Controller
public class AdminBookController {

    @Autowired
    BookService bookService;

    @Autowired
    private CategoryClassService categoryClassService;

    /**
     * 所有图书 或者 二级分类对应图书
     *
     * @param categoryClassId
     * @return
     */
    @RequestMapping("/bookList")
    public Result<CategoryClassBookListVo> bookList(Integer categoryClassId) {
        CategoryClassBookListVo categoryClassBookListVo = new CategoryClassBookListVo();
        if (categoryClassId == null || categoryClassId == 0) {
            List<Book> bookList = bookService.listBookAll();
            categoryClassBookListVo.setBookList(bookList);
        } else {
            CategoryClass categoryClass = categoryClassService.get(categoryClassId);
            List<Book> bookList = bookService.listBook(categoryClass.getCategoryClassname());
            categoryClassBookListVo.setBookList(bookList);
            categoryClassBookListVo.setCategoryClass(categoryClass);
        }
        return Result.success(categoryClassBookListVo);
    }


    /**
     * 获取指定 id 的书
     * @param bookId
     * @return
     */
    @RequestMapping("/getBook")
    public Result<Book> getBook(Integer bookId) {
        if (bookId == null || bookId == 0) {
            return Result.error(ErrorCode.BOOK_NOT_FOUND);
        }
        Book book = bookService.get(bookId);
        return Result.success(book);
    }

    /**
     * 查找书籍, 如果存在分类, 就只在分类中查找, 否则全查询
     *
     * @param m
     * @param categoryClassId
     * @param keyword
     * @return
     */
    @RequestMapping("/searchBooks")
    public Result<List<Book>> searchBooks(Model m, Integer categoryClassId, String keyword) {
        // 没有分类 ,全查询
        if (categoryClassId == 0) {
            List<Book> bookList = bookService.search(keyword);
            return Result.success(bookList);
        }
        // 存在分类, 分类查询
        CategoryClass categoryClass = categoryClassService.get(categoryClassId);
        String categoryClassName = categoryClass.getCategoryClassname();
        List<Book> bookList = bookService.search(keyword, categoryClassName);
        return Result.success(bookList);
    }

    /**
     * 新增图书
     *
     * @param book
     * @return
     */
    @RequestMapping("/addBook")
    public Result<String> addBook(Book book) {
        int ret = bookService.add(book);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("新增图书成功! ");
    }

    /**
     * 编辑图书 页面初始化
     *
     * @param bookId
     * @param categoryClassId
     * @return
     */
    @RequestMapping("/editBook")
    public Result<EditBookVo> editProduct(Integer bookId, Integer categoryClassId) {
        EditBookVo editBookVo = new EditBookVo();
        if (categoryClassId == null || categoryClassId == 0) {
            List<CategoryClass> categoryClasses = categoryClassService.selectAll();
            editBookVo.setCategoryClasses(categoryClasses);
        } else {
            CategoryClass categoryClass = categoryClassService.get(categoryClassId);
            editBookVo.setCategoryClass(categoryClass);
        }
        Book book = bookService.get(bookId);
        editBookVo.setBook(book);
        return Result.success(editBookVo);
    }

    /**
     * 编辑图书
     *
     * @param book
     * @return
     */
    @RequestMapping("/updateBook")
    public Result<String> updateBook(Book book) {
        System.out.println(book.toString());
        int ret = bookService.update(book);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("更新书籍成功! ");
    }


    /**
     * 删除图书
     * @param bookId
     * @return
     */
    @RequestMapping("/deleteBook")
    public Result<String> deleteBook(Integer bookId) {
        int ret = bookService.delete(bookId);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("删除书籍成功! ");
    }

}
