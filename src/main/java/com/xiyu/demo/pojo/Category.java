package com.xiyu.demo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Category {
    private Integer id;

    private String name;

    // 下面的两个属性是为了连表
    private List<Book> bookList;

    private List<List<Book>> bookByRow;
}