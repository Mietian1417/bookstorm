package com.xiyu.demo.service.impl;

import com.xiyu.demo.mapper.AddressMapper;
import com.xiyu.demo.pojo.Address;
import com.xiyu.demo.pojo.AddressExample;
import com.xiyu.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-01
 * Time: 09:30
 *
 * @author 陈子豪
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressMapper;

    @Override
    public int addAddress(Address address) {
        return addressMapper.insertSelective(address);

    }

    @Override
    public List<Address> getByuserId(Integer user_id) {
        AddressExample addressExample = new AddressExample();
        addressExample.or().andUseridEqualTo(user_id);
        return addressMapper.selectByExample(addressExample);
    }

    @Override
    public int updateAddress(Address address) {
        return addressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public Address getById(Integer id) {

        Address address = addressMapper.selectByPrimaryKey(id);
        return address;
    }

    @Override
    public int deleteById(Integer id) {
        return addressMapper.deleteByPrimaryKey(id);
    }
}
