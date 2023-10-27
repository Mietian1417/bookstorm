package com.xiyu.demo.service;


import com.xiyu.demo.pojo.Comment;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 12:01
 *
 * @author 陈子豪
 */
public interface CommentService {

    /**
     * 根据 product_id 来返回当前产品下的所有评论
     *
     * @param bookId
     * @return
     */
    List<Comment> listByBookId(Integer bookId);

    /**
     * 返回当前产品下评论的数量
     */

    int getCount(int book_id);

    /**
     * 增加一条评论
     *
     * @param comment
     */
    int add(Comment comment);

    List<Comment> getAllComments();
}
