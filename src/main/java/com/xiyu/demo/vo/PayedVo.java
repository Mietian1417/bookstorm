package com.xiyu.demo.vo;

import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.Order;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-15
 * Time: 19:12
 *
 * @author 陈子豪
 */

@Data
public class PayedVo {
    // 支付订单
    private Order order;
    // 根据历史痕迹推荐书籍
    private List<Book> basedOnBrowsingHistoryBooks;
}
