package com.xiyu.demo.service;


import com.xiyu.demo.pojo.Order;
import com.xiyu.demo.pojo.OrderItem;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 15:05
 *
 * @author 陈子豪
 */
public interface OrderService {

    /**
     * 等待支付状态，即订单已创建但尚未支付
     */
    String WAIT_PAY = "waitPay";
    /**
     * 等待发货状态，即订单已支付但尚未发货
     */
    String WAIT_DELIVERY = "waitDelivery";
    /**
     * 等待收货状态，即订单已发货但尚未被确认收货
     */
    String WAIT_CONFIRM = "waitConfirm";
    /**
     * 等待评论状态，即订单已完成，但尚未被用户评论
     */
    String WAIT_REVIEW = "waitReview";
    /**
     * 订单已完成状态，即订单已支付、发货、确认收货并评论完毕
     */
    String FINISH = "finish";
    /**
     * 删除状态，即订单被删除或取消
     */
    String DELETE = "delete";

    /**
     * 根据ID返回对应的Order
     *
     * @param id
     * @return
     */
    Order get(Integer id);

    /**
     * 返回所有的订单
     *
     * @return
     */
    List<Order> listAll();

    /**
     * 按照用户、订单状态来查询
     *
     * @param user_id
     * @param excludedStatus
     * @return
     */

    List<Order> list(Integer user_id, String excludedStatus);

    /**
     * 更新订单
     *
     * @param order
     */

    int update(Order order);

    /**
     * 增加订单，返回一个float类型的数值用来表示该订单的总价
     *
     * @param order
     * @param orderItems
     * @return
     */
    float add(Order order, List<OrderItem> orderItems);

    Integer getStatusNum(String s, int userId);

    int deleteOrderItem(int orderId);

    int deleteOrder(int orderId);

    List<Order> searchOrder(String keyword);
}


