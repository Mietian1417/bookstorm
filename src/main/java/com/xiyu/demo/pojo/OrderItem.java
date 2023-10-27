package com.xiyu.demo.pojo;

import lombok.Data;

/**
 * 订单详情, 一个订单详情只能有一本书的信息
 */

@Data
public class OrderItem {
    private Integer id;

    private Integer productId;

    private Integer orderId;

    private Integer userId;

    private Integer number;

    private Book book;

}