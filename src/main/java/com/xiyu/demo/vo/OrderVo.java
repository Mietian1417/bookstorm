package com.xiyu.demo.vo;

import com.xiyu.demo.pojo.Order;
import com.xiyu.demo.pojo.User;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-12
 * Time: 14:52
 *
 * @author 陈子豪
 */

@Data
public class OrderVo {
    // 用户信息
    private User user;
    // 订单
    List<Order> orders;
    // 待支付订单数量
    private int pay;
    // 待发货订单数量
    private int deliver;
    // 待收获订单数量
    private int confirm;
    // 待评论订单数量
    private int review;
    // 已完成订单数量
    private int finish;
}
