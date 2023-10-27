package com.xiyu.demo.service;

import com.xiyu.demo.pojo.Address;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-01
 * Time: 09:18
 *
 * @author 陈子豪
 */
public interface AddressService {

    int addAddress(Address address);

    List<Address> getByuserId(Integer userId);

    int updateAddress(Address address);

    Address getById(Integer id);

    int deleteById(Integer id);
}
