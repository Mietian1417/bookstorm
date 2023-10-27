package com.xiyu.demo.service;

import com.xiyu.demo.pojo.Order;
import com.xiyu.demo.pojo.OrderItem;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 14:31
 *
 * @author 陈子豪
 */
public interface OrderItemService {

    /**
     * 这里返回的 List<OrderItem> 是被处理过的, 已经有了对应的书籍信息
     *
     * @param userId
     * @return
     */
    List<OrderItem> listForCart(Integer userId);

    /**
     * 返回购物车信息
     *
     * @param usrerId
     * @return
     */
    List<OrderItem> listForCartByUserId(Integer usrerId);

    /**
     * 为 List<OrderVo> 填充订单项(属性赋回)
     *
     * @param orders
     */
    void fill(List<Order> orders);


    /**
     * 订单中的属性赋回
     *
     * @param order
     */
    void fill(Order order);

    /**
     * 更新OrderItem，也只是更新number属性
     *
     * @param orderItem
     */
    int update(OrderItem orderItem);

    int add(OrderItem orderItem);

    OrderItem getById(Integer id);

    /**
     * 删除购物车书籍
     *
     * @param userId
     * @param bookId
     * @return
     */
    Integer deleteCartId(Integer userId, Integer bookId);


}
