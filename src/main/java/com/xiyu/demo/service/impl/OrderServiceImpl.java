package com.xiyu.demo.service.impl;

import com.xiyu.demo.mapper.OrderItemMapper;
import com.xiyu.demo.mapper.OrderMapper;
import com.xiyu.demo.pojo.*;
import com.xiyu.demo.service.OrderItemService;
import com.xiyu.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 15:15
 *
 * @author 陈子豪
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public Order get(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> listAll() {
        OrderExample example = new OrderExample();
        return orderMapper.selectByExample(example);
    }

    @Override
    public int update(Order order) {
       return orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public List<Order> list(Integer userId, String excludedStatus) {
        OrderExample example = new OrderExample();
        example.or().andUser_idEqualTo(userId).andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }

    /**
     * 新增订单 并且返回订单总价格
     * @param order
     * @param orderItems
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float add(Order order, List<OrderItem> orderItems) {
        float total = 0;
        orderMapper.insertSelective(order);
        for (OrderItem oi : orderItems) {
            oi.setOrderId(order.getId());
            orderItemService.update(oi);
            total += oi.getBook().getPrice() * oi.getNumber();
        }
        return total;
    }

    @Override
    public Integer getStatusNum(String s, int userId) {
        OrderExample o = new OrderExample();
        o.or().andStatusEqualTo(s).andUser_idEqualTo(userId);
        List<Order> orders = orderMapper.selectByExample(o);
        return orders.size();
    }

    @Override
    public int deleteOrderItem(int orderId) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.or().andOrderIdEqualTo(orderId);
        int i = orderItemMapper.deleteByExample(orderItemExample);
        return i;
    }

    @Override
    public int deleteOrder(int orderId) {
        return orderMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public List<Order> searchOrder(String keyword) {
        OrderExample orderExample = new OrderExample();
        String s1 = "%" + keyword + "%";
        orderExample.or().andOrder_codeLike(s1);
        orderExample.or().andReceiverLike(s1);
        orderExample.or().andAddressLike(s1);
        orderExample.or().andMobileLike(s1);
        return orderMapper.selectByExample(orderExample);
    }
}
