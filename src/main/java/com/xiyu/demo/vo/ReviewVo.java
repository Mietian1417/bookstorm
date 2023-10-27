package com.xiyu.demo.vo;

import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.Order;
import lombok.Data;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-15
 * Time: 21:19
 *
 * @author 陈子豪
 */

@Data
public class ReviewVo {
    private Order order;
    private Book book;
}
