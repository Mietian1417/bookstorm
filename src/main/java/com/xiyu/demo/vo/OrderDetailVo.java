package com.xiyu.demo.vo;

import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.Order;
import com.xiyu.demo.pojo.User;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-12
 * Time: 16:51
 *
 * @author 陈子豪
 */

@Data
public class OrderDetailVo {
    private User user;
    private Book book;
    private Order order;
}
