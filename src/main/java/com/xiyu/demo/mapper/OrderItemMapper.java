package com.xiyu.demo.mapper;

import com.xiyu.demo.pojo.OrderItem;
import com.xiyu.demo.pojo.OrderItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * order_item, 订单子表
 * +------------+---------+------+-----+---------+----------------+
 * | Field      | Type    | Null | Key | Default | Extra          |
 * +------------+---------+------+-----+---------+----------------+
 * | id         | int(11) | NO   | PRI | NULL    | auto_increment |
 * | product_id | int(11) | YES  | MUL | NULL    |                |
 * | order_id   | int(11) | YES  | MUL | NULL    |                |
 * | user_id    | int(11) | NO   | MUL | NULL    |                |
 * | number     | int(11) | YES  |     | NULL    |                |
 * +------------+---------+------+-----+---------+----------------+
 * number 购买书本的数量
 * 订单表, 关联书本详情, 订单主表, 用户详情,
 * -> 如果 order_id 为 NULL, 表示该书本还存在于购物车中, 如果 order_id 不为 NULL, 表示已经生成订单了
 */

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-09-20
 * Time: 23:55
 *
 * @author 陈子豪
 */
public interface OrderItemMapper {
    long countByExample(OrderItemExample example);

    int deleteByExample(OrderItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    List<OrderItem> selectByExample(OrderItemExample example);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderItem record, @Param("example") OrderItemExample example);

    int updateByExample(@Param("record") OrderItem record, @Param("example") OrderItemExample example);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}