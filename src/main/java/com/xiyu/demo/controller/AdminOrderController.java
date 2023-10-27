package com.xiyu.demo.controller;

import com.xiyu.demo.pojo.Order;
import com.xiyu.demo.result.ErrorCode;
import com.xiyu.demo.result.Result;
import com.xiyu.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-23
 * Time: 19:38
 *
 * @author 陈子豪
 */
@Controller
@RequestMapping("/admin")
@ResponseBody
public class AdminOrderController {

    @Autowired
    OrderService orderService;

    /**
     * 所有用户
     * @return
     */
    @RequestMapping("/orderList")
    public Result<List<Order>> orderList() {
        List<Order> orderList = orderService.listAll();
        return Result.success(orderList);
    }

    /**
     * 查询订单
     * @param keyword
     * @return
     */
    @RequestMapping("/searchOrders")
    public Result<List<Order>> searchOrders(String keyword) {
        List<Order> orderList = orderService.searchOrder(keyword);
        return Result.success(orderList);
    }

    /**
     * 订单发货
     * @param orderId
     * @return
     */
    @RequestMapping("orderDelivery")
    public Result<String> delivery(Integer orderId) {
        Order order = orderService.get(orderId);
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.WAIT_CONFIRM);
        int ret = orderService.update(order);
        if (ret == 0){
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("发货成功! ");
    }
}
