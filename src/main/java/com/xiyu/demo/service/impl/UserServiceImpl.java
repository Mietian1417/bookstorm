package com.xiyu.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiyu.demo.mapper.UserMapper;
import com.xiyu.demo.pojo.User;
import com.xiyu.demo.pojo.UserExample;
import com.xiyu.demo.service.UserService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 16:39
 *
 * @author 陈子豪
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        UserExample example = new UserExample();
        return userMapper.selectByExample(example);
    }

    @Override
    public void updatePassword(int id, String password) {
        User user = get(id);
        user.setPassword(password);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User get(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User get(String name, String password) {
        UserExample example = new UserExample();
        example.or().andNameEqualTo(name).andPasswordEqualTo(password);
        List<User> result = userMapper.selectByExample(example);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public boolean isExist(String name) {
        UserExample example = new UserExample();
        example.or().andNameEqualTo(name);
        List<User> result = userMapper.selectByExample(example);
        return !result.isEmpty();
    }

    @Override
    public int add(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int delete(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> searchUser(String s) {
        UserExample userExample = new UserExample();
        String s1 = "%" + s + "%";
        userExample.or().andNameLike(s1);
        userExample.or().andSexLike(s1);
        userExample.or().andEmailLike(s1);
        userExample.or().andPhoneLike(s1);
        return userMapper.selectByExample(userExample);
    }

    /**
     * 上传图片功能, 利用 HTTP客户端 来访问第三方服务(Imgbb), 来保存图片
     * @param imageFile 上传的文件
     * @return 图片的 url
     */
    @Override
    public String uploadImageToImgbb(MultipartFile imageFile) {
        // API url
        String url = "https://api.imgbb.com/1/upload";
        // Imgbb 的 API 秘钥
        String apiKey = "d9101f17a07e441a57cd4c4baeb349bc";

        try {
            // 创建HTTP客户端
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 创建POST请求
            HttpPost httpPost = new HttpPost(url);
            // 创建Multipart实体，用于上传文件
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.addBinaryBody("image", imageFile.getBytes(), ContentType.DEFAULT_BINARY, imageFile.getOriginalFilename());
            entityBuilder.addTextBody("key", apiKey);
            // 设置请求实体
            httpPost.setEntity(entityBuilder.build());
            // 执行POST请求
            HttpResponse response = httpClient.execute(httpPost);
            // 读取响应内容
            String responseBody = EntityUtils.toString(response.getEntity());
            // 关闭HTTP客户端
            httpClient.close();
            // 解析JSON响应
            JSONObject jsonResponse = JSON.parseObject(responseBody);
            // 提取URL属性的值
            return jsonResponse.getJSONObject("data").getString("url");
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
            return null;
        }
    }
}
