package com.xiyu.demo.vo.admin;

import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.CategoryClass;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-23
 * Time: 16:50
 *
 * @author 陈子豪
 */

@Data
public class CategoryClassBookListVo {

    private List<Book> bookList;

    private CategoryClass categoryClass;
}
