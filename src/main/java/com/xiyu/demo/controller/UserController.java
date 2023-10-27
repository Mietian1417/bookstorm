package com.xiyu.demo.controller;

import com.xiyu.demo.exception.CustomException;
import com.xiyu.demo.pojo.*;
import com.xiyu.demo.result.ErrorCode;
import com.xiyu.demo.result.Result;
import com.xiyu.demo.service.*;
import com.xiyu.demo.util.FileUploadUtils;
import com.xiyu.demo.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-23
 * Time: 23:55
 *
 * @author 陈子豪
 */
@Controller
@ResponseBody
public class UserController {

    @Value("${user.uploadImageDir}")
    private String uploadImageDir;

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    OrderService orderService;

    @Autowired
    BookService bookService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    CategoryClassService categoryClassService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ScoreService scoreService;

    @Autowired
    CommentService commentService;

    /**
     * 主页跳转
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/")
    public void redirectToIndex(HttpServletResponse response) throws IOException {
        response.sendRedirect("/views/index.html");
    }

    /**
     * 登录验证
     *
     * @param name
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public Result<String> login(@RequestParam("name") String name,
                                @RequestParam("password") String password,
                                HttpSession session) {
        User user = userService.get(name, password);
        if (null == user) {
            return Result.error(ErrorCode.USER_OR_PASSWORD_ERROR);
        }
        if ("admin".equals(name)) {
            session.setAttribute("user", user);
            return Result.success("admin");
        }
        session.setAttribute("user", user);
        return Result.success("登陆成功! ");
    }

    /**
     * 注销
     *
     * @param session
     * @param response
     * @throws IOException
     */
    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.removeAttribute("user");
        response.sendRedirect("/views/index.html");
    }

    /**
     * 注册逻辑
     *
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public Result<String> register(User user) {
        String name = user.getName();
        boolean userIsExist = userService.isExist(name);
        if (userIsExist) {
            return Result.error(ErrorCode.USER_EXISTS);
        }
        int i = userService.add(user);
        if (i == 1) {
            return Result.success("注册成功! ");
        } else {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }

    }

    /**
     * 个人中心
     *
     * @param session
     * @return
     */
    @RequestMapping("/personalCenter")
    public Result<PersonalCenterVo> personalCenter(HttpSession session) {
        User user = (User) session.getAttribute("user");
        int pay = orderService.getStatusNum("waitPay", user.getId());
        int deliver = orderService.getStatusNum("waitDelivery", user.getId());
        int confirm = orderService.getStatusNum("waitConfirm", user.getId());
        int review = orderService.getStatusNum("waitReview", user.getId());

        PersonalCenterVo personalCenter = new PersonalCenterVo();
        personalCenter.setUser(user);
        personalCenter.setPay(pay);
        personalCenter.setDeliver(deliver);
        personalCenter.setConfirm(confirm);
        personalCenter.setReview(review);
        return Result.success(personalCenter);
    }

    /**
     * 个人信息
     *
     * @param session
     * @return
     */
    @RequestMapping("/mineMessage")
    public Result<User> message(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return Result.success(user);
    }

    /**
     * 上传图片功能, 利用 HTTP客户端 来访问第三方服务器来保存图片
     *
     * @param file    用户上传的图片
     * @param session
     * @return 图片的 url
     */
    @PostMapping("/updateImg1")
    public Result<String> uploadFile1(MultipartFile file, HttpSession session) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // 监听上传 5s
        Future<Result<String>> resultFuture = executor.submit(() -> {
            String url = userService.uploadImageToImgbb(file);
            User user = (User) session.getAttribute("user");
            user.setImage(url);
            int ret = userService.update(user);
            if (ret == 0) {
                return Result.error(ErrorCode.DATABASE_ERROR);
            }
            // 更新 session
            session.setAttribute("user", user);
            return Result.success(url);
        });

        try {
            // 等待上传操作完成，最多等待5秒
            return resultFuture.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            // 处理异常
            return Result.error(ErrorCode.UPLOAD_ERROR);
        } catch (TimeoutException e) {
            // 上传超时处理
            // 取消任务
            resultFuture.cancel(true);
            return Result.error(ErrorCode.UPLOAD_TIMEOUT);
        }
    }

    /**
     * 上传图片功能, 保存到本地返回 url
     *
     * @param file    上传的图片
     * @param session
     * @return 图片的 url
     */
    @PostMapping("/updateImg")
    public Result<String> uploadFile(MultipartFile file, HttpSession session) {
        if (file != null && !file.isEmpty()) {
            User user = (User) session.getAttribute("user");
            if (user == null || user.getId() == null) {
                return Result.error(new ErrorCode(-1, "用户信息不完整，无法上传图片"));
            }
            try {
                // 生成新文件名
                String newFileName = FileUploadUtils.generateUniqueFileName(user.getId(), file);
                // 保存文件
                FileUploadUtils.saveFile(file, newFileName, uploadImageDir);

                // 更新用户的图片URL
                String imageUrl = "/userImage/" + newFileName;
                // 记录修改之前的 imageUrl
                String oldImageUrl = user.getImage();
                user.setImage(imageUrl);
                // 更新用户信息
                int ret = userService.update(user);
                if (ret == 0) {
                    return Result.error(new ErrorCode(-1, "上传失败"));
                }

                // 获取原图片的文件全名
                int lastIndex = oldImageUrl.lastIndexOf("/");
                oldImageUrl = oldImageUrl.substring(lastIndex + 1);
                // 数据库保存的是/userImage/***.jpg, Dir中也有 userImage 路径
                String oldImagePath = uploadImageDir + oldImageUrl;
                // 删除用户原本的图片
                FileUploadUtils.deleteOldImage(oldImageUrl, oldImagePath);
                // 更新 session 中的用户信息
                session.setAttribute("user", user);
                return Result.success(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.error(new ErrorCode(-1, "上传失败, 请联系管理人员!"));
            }
        } else {
            return Result.error(new ErrorCode(-1, "上传失败：文件为空"));
        }
    }


    /**
     * 文件上传时间过长, 后续上传功能返回 url
     *
     * @param session
     * @return
     */
    @GetMapping("/checkUploadStatus")
    public Result<String> checkUploadStatus(HttpSession session) {
        User u = (User) session.getAttribute("user");
        User user = userService.get(u.getId());
        // 如果不一致说明图片上传成功, 否则上传还在继续
        if (u.getImage().equals(user.getImage())) {
            return Result.error(ErrorCode.UPLOAD_TIMEOUT);
        } else {
            // 成功上传, 更新 session
            session.setAttribute("user", user);
            return Result.success(user.getImage());
        }
    }


    /**
     * 修改个人信息
     *
     * @param session
     * @param user
     * @return
     */
    @RequestMapping("/updateMyMessage")
    public Result<User> updateMyMessage(HttpSession session, User user) {
        // 更新依赖用户 id, 将前端传输来的 user 设置 id
        User u = (User) session.getAttribute("user");
        // 注意补全 user 信息
        user.setPassword(u.getPassword());
        user.setImage(u.getImage());
        user.setId(u.getId());
        // 更新用户数据
        int ret = userService.update(user);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        // 更新 session
        session.setAttribute("user", user);
        return Result.success(user);
    }

    /**
     * 修改密码
     *
     * @param session
     * @param oldPassword
     * @param newPassword
     * @param againPassword
     * @return
     */
    @RequestMapping(value = "/updatePassword")
    public Result<String> updateMyPassword(HttpSession session, String oldPassword, String newPassword, String againPassword) {
        User user = (User) session.getAttribute("user");
        String password = user.getPassword();
        if (!password.equals(oldPassword)) {
            return Result.error(ErrorCode.OLD_PASSWORD_ERROR);
        }
        if (!newPassword.equals(againPassword)) {
            return Result.error(ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }
        userService.updatePassword(user.getId(), newPassword);
        user.setPassword(newPassword);
        session.setAttribute("user", user);
        return Result.success("修改成功! ");
    }

    /**
     * 地址界面
     *
     * @param session
     * @return
     */
    @RequestMapping("/address")
    public Result<AddressVo> address(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Address> addressList = addressService.getByuserId(user.getId());
        AddressVo addressVo = new AddressVo();
        addressVo.setUser(user);
        addressVo.setAddressList(addressList);
        return Result.success(addressVo);
    }

    /**
     * 新增地址
     *
     * @param address
     * @param session
     * @return
     */
    @RequestMapping("/addAddress")
    public Result<String> addAddress(Address address, HttpSession session) {
        User user = (User) session.getAttribute("user");
        // 地址依赖于用户 id
        address.setUserid(user.getId());
        int ret = addressService.addAddress(address);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("新增地址成功! ");
    }

    /**
     * 删除地址
     *
     * @param addressId
     * @return
     */
    @RequestMapping("/deleteAddress")
    public Result<String> deleteAddress(Integer addressId) {
        int ret = addressService.deleteById(addressId);
        if (ret == 0) {
            return Result.error(ErrorCode.ADDRESS_EXIST_ORDER_ERROR);
        }
        return Result.success("删除地址成功! ");
    }

    /**
     * 获取 updateAddress.html 数据
     *
     * @param addressId
     * @param session
     * @return
     */
    @RequestMapping("/getUpdateAddressData")
    public Result<UpdateAddressVo> getUpdateAddressData(Integer addressId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        // 更新地址, 依赖用户 id
        Address address = addressService.getById(addressId);
        if (address == null) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        UpdateAddressVo updateAddressVo = new UpdateAddressVo();
        updateAddressVo.setUser(user);
        updateAddressVo.setAddress(address);
        return Result.success(updateAddressVo);
    }

    /**
     * 更新地址
     *
     * @param address
     * @param session
     * @return
     */
    @RequestMapping("/updateAddress")
    public Result<String> updateAddress(Address address, HttpSession session) {
        User user = (User) session.getAttribute("user");
        // 更新地址, 依赖用户 id
        address.setUserid(user.getId());
        int ret = addressService.updateAddress(address);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("更新地址成功! ");
    }

    /**
     * 我的订单
     *
     * @param session
     * @return
     */
    @RequestMapping("/myOrder")
    public Result<OrderVo> myOrder(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.list(user.getId(), "delete");
        // 所有订单属性赋回
        orderItemService.fill(orders);

        int pay = orderService.getStatusNum("waitPay", user.getId());
        int deliver = orderService.getStatusNum("waitDelivery", user.getId());
        int confirm = orderService.getStatusNum("waitConfirm", user.getId());
        int review = orderService.getStatusNum("waitReview", user.getId());
        int finish = orderService.getStatusNum("finish", user.getId());

        OrderVo orderVo = new OrderVo();
        orderVo.setUser(user);
        orderVo.setOrders(orders);

        orderVo.setPay(pay);
        orderVo.setDeliver(deliver);
        orderVo.setConfirm(confirm);
        orderVo.setReview(review);
        orderVo.setFinish(finish);
        return Result.success(orderVo);
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @param bookId
     * @return
     */
    @RequestMapping("/orderDetail")
    public Result<OrderDetailVo> orderDetail(Integer orderId, Integer bookId, HttpSession session) {
        Order order = orderService.get(orderId);
        orderItemService.fill(order);
        Book book = bookService.get(bookId);
        User user = userService.get(order.getUserId());
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setUser(user);
        orderDetailVo.setBook(book);
        orderDetailVo.setOrder(order);
        return Result.success(orderDetailVo);
    }

    /**
     * 购物车
     *
     * @param session
     * @return
     */
    @RequestMapping("/cart")
    public Result<List<OrderItem>> cart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listForCart(user.getId());
        return Result.success(orderItems);
    }

    /**
     * 加入购物车方法，跟buyone()方法有些类似，但返回不同
     * 仍然需要新增订单项OrderItem，考虑两个情况：
     * 1.如果这个产品已经存在于购物车中，那么只需要相应的调整数量就可以了
     * 2.如果不存在对应的OrderItem项，那么就新增一个订单项（OrderItem）
     * - 前提条件：已经登录
     *
     * @param bookId
     * @param number
     * @param session
     * @return
     */
    @RequestMapping("addCart")
    public Result<String> addCart(Integer bookId, Integer number, HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean found = false;
        // 查询购物车
        List<OrderItem> orderItems = orderItemService.listForCartByUserId(user.getId());
        // 购物车存在则更新, 不存在则添加
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProductId().equals(bookId)) {
                orderItem.setNumber(orderItem.getNumber() + number);
                orderItem.setOrderId(null);
                orderItemService.update(orderItem);
                found = true;
                break;
            }
        }

        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUserId(user.getId());
            orderItem.setNumber(number);
            orderItem.setProductId(bookId);
            orderItemService.add(orderItem);
        }
        return Result.success("添加购物车成功! ");
    }

    /**
     * 修改购物车中的书籍数量
     *
     * @param session
     * @return
     */
    @RequestMapping("/changeCart")
    public Result<String> changeCart(HttpSession session, Integer bookId, Integer number) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listForCartByUserId(user.getId());
        OrderItem orderItem = null;
        for (OrderItem item : orderItems) {
            if (item.getProductId().equals(bookId)) {
                orderItem = item;
                break;
            }
        }
        if (orderItem == null) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        orderItem.setNumber(number);
        int ret = orderItemService.update(orderItem);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("更新购物车成功! ");
    }

    /**
     * 从购物车中删除书籍
     *
     * @param session
     * @return
     */
    @RequestMapping("/deleteCartBook")
    public Result<String> deleteCartBook(HttpSession session, Integer bookId) {
        User user = (User) session.getAttribute("user");
        int ret = orderItemService.deleteCartId(user.getId(), bookId);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("删除购物车书籍成功! ");
    }

    /**
     * 分类书籍详情
     *
     * @param categoryClassId 二级分类id
     * @return
     */
    @RequestMapping("categoryDetail")
    public Result<CategoryDetailVo> categoryDetail(Integer categoryClassId) {
        CategoryClass categoryClass = categoryClassService.get(categoryClassId);
        // 二级分类名称
        String categoryClassName = categoryClass.getCategoryClassname();
        // // 该分类的书籍数量
        // long bookCount = bookService.getBookCountByCategoryClass(categoryClassName);
        List<Book> bookList = bookService.getAllBooksByCategoryClass(categoryClassName);
        // 该分类所有书籍的所有评分
        // long[] categoryClassScores = scoreService.getAllScoreByCategoryClass(categoryClassName);
        long[] categoryClassScores = scoreService.getAllScoreByCategoryClass(bookList);
        CategoryDetailVo categoryDetailVo = new CategoryDetailVo();
        categoryDetailVo.setCategoryClass(categoryClass);
        categoryDetailVo.setBookCount(bookList.size());
        categoryDetailVo.setCategoryClassScores(categoryClassScores);
        return Result.success(categoryDetailVo);
    }

    /**
     * 分类的书籍
     *
     * @param categoryClassId 书本id
     * @param queryWay        查询方式
     * @param page            第几页
     * @return
     */
    @RequestMapping("/categoryDetailBooks")
    public Result<List<Book>> categoryDetailBooks(Integer categoryClassId, String queryWay, Integer page) {
        CategoryClass categoryClass = categoryClassService.get(categoryClassId);
        String categoryClassName = categoryClass.getCategoryClassname();
        List<Book> bookList = bookService.getListBook(categoryClassName, queryWay, page * 20);
        return Result.success(bookList);
    }

    /**
     * 删除订单
     *
     * @param orderId
     * @return
     */
    @RequestMapping("deleteOrder")
    public Result<String> deleteOrder(Integer orderId) {
        // 先删除订单项数据
        int deletedOrderItemsCount = orderService.deleteOrderItem(orderId);
        // 再删除订单主表数据
        int deletedOrdersCount = orderService.deleteOrder(orderId);
        if (deletedOrderItemsCount == 0 || deletedOrdersCount == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success("删除成功! ");
    }

    // @RequestMapping("test")
    // public void test() {
    //     List<Comment> allComments = commentService.getAllComments();
    //     for (Comment comment : allComments) {
    //         scoreService.addScore(comment.getUserid(), comment.getBookid(), comment.getCommentScore());
    //     }
    // }
}
