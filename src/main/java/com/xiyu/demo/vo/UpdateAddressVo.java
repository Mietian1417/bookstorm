package com.xiyu.demo.vo;

import com.xiyu.demo.pojo.Address;
import com.xiyu.demo.pojo.User;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-12
 * Time: 20:27
 *
 * @author 陈子豪
 */

@Data
public class UpdateAddressVo {
    private User user;
    private Address address;
}
