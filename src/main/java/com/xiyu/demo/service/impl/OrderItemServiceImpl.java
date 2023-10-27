package com.xiyu.demo.service.impl;

import com.xiyu.demo.mapper.OrderItemMapper;
import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.Order;
import com.xiyu.demo.pojo.OrderItem;
import com.xiyu.demo.pojo.OrderItemExample;
import com.xiyu.demo.service.BookService;
import com.xiyu.demo.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 14:40
 *
 * @author 陈子豪
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    BookService bookService;

    /**
     * 查看购物车
     *
     * @param userId
     * @return
     */
    @Override
    public List<OrderItem> listForCartByUserId(Integer userId) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.or().andUserIdEqualTo(userId).andOrderIdIsNull();
        return orderItemMapper.selectByExample(orderItemExample);
    }

    /**
     * List<OrderItem> 内部处理, 直接将书籍信息带出
     * @param userId
     * @return
     */
    @Override
    public List<OrderItem> listForCart(Integer userId) {
        OrderItemExample example = new OrderItemExample();
        // 购物车中的书本必须没有生成订单, 否则对应的书本是已经结算的状态
        example.or().andUserIdEqualTo(userId).andOrderIdIsNull();
        List<OrderItem> result = orderItemMapper.selectByExample(example);
        // 查询出来的 OrderItem 只有书籍id, 没有书
        setProduct(result);
        return result;
    }

    public void setProduct(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            setProduct(orderItem);
        }
    }

    private void setProduct(OrderItem orderItem) {
        Book books = bookService.get(orderItem.getProductId());
        orderItem.setBook(books);
    }

    /**
     * 为 List<OrderVo> 填充订单项(属性赋回)
     * @param orders
     */
    @Override
    public void fill(List<Order> orders) {
        for (Order o : orders) {
            fill(o);
        }
    }

    /**
     * 订单属性赋回
     * @param order
     */
    @Override
    public void fill(Order order) {
        OrderItemExample example = new OrderItemExample();
        example.or().andOrderIdEqualTo(order.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        // 把所有书籍赋回
        setProduct(orderItems);
        // 总价格
        float total = 0;
        // 总数量
        int totalNumber = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getNumber() * orderItem.getBook().getPrice();
            totalNumber += orderItem.getNumber();
        }
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.setOrderItems(orderItems);
    }

    @Override
    public int update(OrderItem orderItem) {
        return orderItemMapper.updateByPrimaryKey(orderItem);
    }

    /**
     * 新增购物车
     * @param orderItem
     * @return
     */
    @Override
    public int add(OrderItem orderItem) {
        return orderItemMapper.insertSelective(orderItem);
    }

    /**
     * 删除购物车项, 按照 id
     * @param id
     * @return
     */
    @Override
    public OrderItem getById(Integer id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;
    }

    /**
     * 删除购物车书籍
     * @param userId
     * @param bookId
     * @return
     */
    @Override
    public Integer deleteCartId(Integer userId, Integer bookId) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.or().andUserIdEqualTo(userId).andProductIdEqualTo(bookId).andOrderIdIsNull();
        return orderItemMapper.deleteByExample(orderItemExample);
    }
}
