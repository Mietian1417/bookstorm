package com.xiyu.demo.vo;

import com.xiyu.demo.pojo.User;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-11
 * Time: 18:42
 *
 * @author 陈子豪
 */

@Data
public class PersonalCenterVo {
    // 用户信息
    private User user;
    // 待支付订单数量
    private int pay;
    // 待发货订单数量
    private int deliver;
    // 待收获订单数量
    private int confirm;
    // 待评论订单数量
    private int review;
}
