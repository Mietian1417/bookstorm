package com.xiyu.demo.vo;

import com.xiyu.demo.pojo.Address;
import com.xiyu.demo.pojo.OrderItem;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-15
 * Time: 17:12
 *
 * @author 陈子豪
 */

/**
 * 提交订单
 */
@Data
public class BuyVo {
    private List<Address> addressList;
    private List<OrderItem> orderItems;
    private float total;
}
