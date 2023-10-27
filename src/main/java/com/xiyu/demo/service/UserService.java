package com.xiyu.demo.service;

import com.xiyu.demo.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 16:20
 *
 * @author 陈子豪
 */
public interface UserService {

    /**
     * 返回所有的用户
     *
     * @return
     */
    List<User> list();

    /**
     * 更改用户密码
     *
     * @param id
     * @param password
     */
    void updatePassword(int id, String password);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    User get(Integer id);

    /**
     * 根据用户名和密码来查询用户
     *
     * @param name
     * @param password
     * @return
     */
    User get(String name, String password);

    /**
     * 根据用户名判断用户是否存在
     *
     * @param name
     * @return
     */
    boolean isExist(String name);

    /**
     * 增加一条用户数据
     *
     * @param user
     */
    int add(User user);

    int delete(Integer id);

    int update(User user);

    List<User> searchUser(String s);

    /**
     * 上传图片功能, 利用 HTTP客户端 来访问第三方服务(Imgbb), 来保存图片
     * @param imageFile 上传的文件
     * @return 图片的 url
     */
    String uploadImageToImgbb(MultipartFile imageFile);
}
