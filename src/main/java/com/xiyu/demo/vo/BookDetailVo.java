package com.xiyu.demo.vo;

import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.Comment;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-14
 * Time: 21:16
 *
 * @author 陈子豪
 */

@Data
public class BookDetailVo {
    // 相似书籍
    private List<Book> similarBooks;
    // 用户可能喜欢
    private List<Book> userMaybeLikeBooks;
    // 书籍详情
    private Book book;
    // 书籍评论
    private List<Comment> comments;
    // 该书打分情况
    private long[] scores;
}
