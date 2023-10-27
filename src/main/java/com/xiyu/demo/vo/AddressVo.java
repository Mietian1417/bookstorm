package com.xiyu.demo.vo;

import com.xiyu.demo.pojo.Address;
import com.xiyu.demo.pojo.User;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-12
 * Time: 18:12
 *
 * @author 陈子豪
 */

@Data
public class AddressVo {
    private User user;
    private List<Address> addressList;
}
