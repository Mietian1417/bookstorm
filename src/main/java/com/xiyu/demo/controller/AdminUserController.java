package com.xiyu.demo.controller;

import com.xiyu.demo.pojo.User;
import com.xiyu.demo.result.ErrorCode;
import com.xiyu.demo.result.Result;
import com.xiyu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-23
 * Time: 20:48
 *
 * @author 陈子豪
 */
@Controller
@RequestMapping("/admin")
@ResponseBody
public class AdminUserController {
    @Autowired
    UserService userService;

    /**
     * 我的桌面
     * @return
     */
    @RequestMapping("/myDesktop")
    public String myDesktop() {
        return new Date().toString();
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @RequestMapping("/userList")
    public Result<List<User>> userList() {
        List<User> userList = userService.list();
        return Result.success(userList);
    }

    /**
     * 查询用户
     *
     * @param keyword
     * @return
     */
    @RequestMapping("/searchUsers")
    public Result<List<User>> searchUsers(String keyword) {
        if (keyword == null || "".equals(keyword)) {
            return Result.error(new ErrorCode(-1, "查询词不能为空! "));
        } else {
            List<User> users = userService.searchUser(keyword);
            return Result.success(users);
        }
    }

    /**
     * 返回指定要编辑用户的信息
     * @param userId
     * @return
     */
    @RequestMapping("/editUser")
    public Result<User> editUser(Integer userId) {
        User user = userService.get(userId);
        return Result.success(user);
    }


    /**
     * 新增用户
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    public Result<String> addUser(User user) {
        int ret = userService.add(user);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("增加成功! ");
    }

    /**
     * 编辑用户
     * @param session
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public Result<String> updateUser(HttpSession session, User user) {
        User u = (User) session.getAttribute("user");
        user.setId(u.getId());
        int ret = userService.update(user);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("编辑成功! ");
    }

    /**
     * 删除指定 id 用户
     * @param userId
     * @return
     */
    @RequestMapping("/deleteUser")
    public Result<String> deleteUser(Integer userId) {
        int ret = userService.delete(userId);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("删除成功! ");
    }

    /**
     * 删除所有选中的用户
     * @param userId
     * @return
     */
    @RequestMapping("deleteUsers")
    public Result<String> deleteUsers(Integer[] userId) {
        for (Integer id : userId) {
            int ret = userService.delete(id);
            if (ret == 0) {
                return Result.error(ErrorCode.DATABASE_ERROR);
            }
        }
        return Result.success("删除成功! ");
    }
}
