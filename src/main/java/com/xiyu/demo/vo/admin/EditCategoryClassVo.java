package com.xiyu.demo.vo.admin;

import com.xiyu.demo.pojo.Category;
import com.xiyu.demo.pojo.CategoryClass;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-22
 * Time: 22:33
 *
 * @author 陈子豪
 */

@Data
public class EditCategoryClassVo {
    private List<Category> categories;
    private CategoryClass categoryClass;
}
