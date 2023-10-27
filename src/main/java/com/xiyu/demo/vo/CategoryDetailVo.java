package com.xiyu.demo.vo;

import com.xiyu.demo.pojo.CategoryClass;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-12
 * Time: 22:22
 *
 * @author 陈子豪
 */

@Data
public class CategoryDetailVo {
    // 二级分类
    private CategoryClass categoryClass;
    // 书籍数量
    private long bookCount;
    // 分类的评分情况
    private long[] categoryClassScores;
}
