package com.xiyu.demo.controller;

import com.xiyu.demo.pojo.*;
import com.xiyu.demo.result.ErrorCode;
import com.xiyu.demo.result.Result;
import com.xiyu.demo.service.*;
import com.xiyu.demo.service.impl.MyUserBasedRecommenderImpl;
import com.xiyu.demo.vo.*;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-23
 * Time: 22:18
 *
 * @author 陈子豪
 */
@Controller
@ResponseBody
public class CoreController {

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryClassService categoryClassService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    CommentService commentService;

    @Autowired
    AddressService addressService;

    @Autowired
    ScoreService scoreService;

    @Autowired
    BookService bookService;

    @Autowired
    private MyUserBasedRecommender1 myUserBasedRecommender1;

    @Autowired
    MyItemBasedRecommender myItemBasedRecommender;

    /**
     * 主页数据
     *
     * @param session
     * @return
     */
    @RequestMapping("/home")
    public Result<HomeDataVo> home(HttpSession session) {
        HomeDataVo homeData = new HomeDataVo();
        // 用户登录后, 推荐 猜你喜欢 书籍
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            // 用户推荐对象
            // 推荐项
            List<RecommendedItem> guessYouLike = myUserBasedRecommender1.userBasedRecommender(user.getId(), 5);
            // 推荐项转为图书
            List<Book> guessYouLikeBooks = bookService.getRecommend(guessYouLike);
            // 添加猜你喜欢书籍
            homeData.setGuessYouLikeBooks(guessYouLikeBooks);
        }

        List<Book> newBooks = bookService.listBook();
        List<Book> popularBooks = bookService.listBookSale();
        // 新书速递
        homeData.setNewBooks(newBooks);
        // 热门书籍
        homeData.setPopularBooks(popularBooks);

        List<List<CategoryClass>> categoryClasses = new ArrayList<>();
        List<Category> categories = categoryService.list();
        for (Category c : categories) {
            categoryClasses.add(categoryClassService.list(c.getId()));
        }

        // 一级分类
        homeData.setCategories(categories);
        // 二级分类
        homeData.setCategoriesClasses(categoryClasses);
        return Result.success(homeData);
    }

    /**
     * 待收货
     *
     * @param orderId
     * @return
     */
    @RequestMapping("waitConfirm")
    public Result<Order> waitConfirm(Integer orderId) {
        Order order = orderService.get(orderId);
        if (order == null) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        // 单个订单属性赋回, 这样只需要传递一个对象给前端
        orderItemService.fill(order);
        return Result.success(order);
    }

    /**
     * 确认收货
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/receiveConfirmation")
    public Result<Order> orderConfirmed(Integer orderId) {
        Order order = orderService.get(orderId);
        if (order == null) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        // 订单状态待评论
        order.setStatus(OrderService.WAIT_REVIEW);
        order.setConfirmDate(new Date());
        int ret = orderService.update(order);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        // 单个订单属性赋回, 这样只需要传递一个对象给前端
        orderItemService.fill(order);
        return Result.success(order);
    }

    /**
     * 书籍详情页
     *
     * @param bookId
     * @param session
     * @return
     * @throws IOException
     * @throws TasteException
     */
    @RequestMapping("/bookDetail")
    public Result<BookDetailVo> bookDetail(Integer bookId, HttpSession session) throws IOException, TasteException {
        BookDetailVo bookDetailVo = new BookDetailVo();
        Book book = bookService.get(bookId);
        // 根据当前书来推荐相似户籍
        boolean bookHasComments = scoreService.getNumByBookId(bookId);
        if (bookHasComments) {
            long[] bookIdList = myItemBasedRecommender.myItem(bookId);
            // 推荐项转书籍列表
            List<Book> similarBooks = bookService.listBookId(bookIdList);
            bookDetailVo.setSimilarBooks(similarBooks);
        }

        // 如果用户登录, 推荐书籍 买过该书的人还喜欢
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // 推荐书籍 买过该书的人还喜欢
            Set<Integer> userMaybeLikeIds = myUserBasedRecommender1.buyByUser(user.getId(), bookId);
            List<Book> userMaybeLike = new ArrayList<>();
            for (Integer id : userMaybeLikeIds) {
                Book curBook = bookService.get(id);
                userMaybeLike.add(curBook);
            }
            bookDetailVo.setUserMaybeLikeBooks(userMaybeLike);
        }
        bookDetailVo.setBook(book);
        List<Comment> comments = commentService.listByBookId(bookId);
        bookDetailVo.setComments(comments);

        // 获取这本书每一个分值打分的人数
        long[] scores = scoreService.getAllScoreByBook(bookId);
        bookDetailVo.setScores(scores);

        // 下面的代码是为了支付订单后的推荐功能 -> 根据历史浏览记录推荐
        // 将用户浏览过的书存入 session
        LinkedList<Integer> basedOnBrowsingHistoryIds = (LinkedList<Integer>) session.getAttribute("basedOnBrowsingHistory");
        if (basedOnBrowsingHistoryIds == null) {
            // 这里的数据结构选择链表是非常重要的操作
            LinkedList<Integer> list = new LinkedList<>();
            list.add(bookId);
            session.setAttribute("basedOnBrowsingHistory", list);
        } else {
            // 如果该用户最近看过该书详情, 那么将该数的位置重置于第一个
            if (basedOnBrowsingHistoryIds.contains(bookId)) {
                basedOnBrowsingHistoryIds.remove(bookId);
                basedOnBrowsingHistoryIds.addFirst(bookId);
            } else {
                // 该用户没有看过该书, 直接加入第一个位置
                basedOnBrowsingHistoryIds.addFirst(bookId);
            }
            session.setAttribute("basedOnBrowsingHistory", basedOnBrowsingHistoryIds);
        }
        return Result.success(bookDetailVo);
    }


    /**
     * 点击购买, 生成订单项, 跳转到提交订单页
     *
     * @param bookId
     * @param number
     * @param session
     * @return
     */
    @RequestMapping("/buyOne")
    public Result<Integer> buyone(Integer bookId, Integer number, HttpSession session) {
        User user = (User) session.getAttribute("user");
        // 新增订单项
        OrderItem orderItem = new OrderItem();
        // 订单设置购买的人
        orderItem.setUserId(user.getId());
        // 订单设置购买的数量
        orderItem.setNumber(number);
        // 订单设置购买的书籍
        orderItem.setProductId(bookId);
        // 新增订单并返回id到对象的id属性中
        orderItemService.add(orderItem);
        int orderItemId = orderItem.getId();
        if (orderItemId == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success(orderItemId);
    }

    /**
     * 提交订单页
     *
     * @param orderItemId 订单数组, 可能是直接点击书本支付的, 也可能是清空购物车支付的
     * @param session
     * @return
     */
    @RequestMapping("buy")
    public Result<BuyVo> buy(Integer[] orderItemId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        // 查询出该用户的收货地址
        List<Address> addressList = addressService.getByuserId(user.getId());
        // 所有订单商品
        List<OrderItem> orderItems = new ArrayList<>();
        // 订单总金额
        float total = 0;
        for (Integer id : orderItemId) {
            // 查询订单
            OrderItem orderItem = orderItemService.getById(id);
            // 累加该订单金额
            total += orderItem.getBook().getPrice() * orderItem.getNumber();
            // 当前订单加入总订单列表
            orderItems.add(orderItem);
        }

        // 当用户还未提交订单时, 创建订单项的缓存, 方便后续获取订单的订单项
        session.setAttribute("orderItems", orderItems);

        BuyVo buyVo = new BuyVo();
        buyVo.setAddressList(addressList);
        buyVo.setOrderItems(orderItems);
        buyVo.setTotal(total);
        return Result.success(buyVo);
    }

    /**
     * 提交订单
     *
     * @param order
     * @param session
     * @return
     */
    @RequestMapping("createOrder")
    public Result<PayVo> createOrder(Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        // 时间戳作为订单编码
        order.setOrderCode(orderCode);
        // 订单创建日期
        order.setCreateDate(new Date());
        // 订单关联的用户
        order.setUserId(user.getId());
        // 订单状态: 待支付
        order.setStatus(OrderService.WAIT_PAY);
        //  生成订单, 订单项直接从 session 中获取
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("orderItems");
        // 新增订单
        float total = orderService.add(order, orderItems);
        PayVo payVo = new PayVo();
        payVo.setOrderId(order.getId());
        payVo.setTotal(total);
        return Result.success(payVo);
    }

    /**
     * 确认支付
     *
     * @param orderId
     * @return
     * @throws IOException
     * @throws TasteException
     */
    @RequestMapping("payed")
    public Result<PayedVo> payed(int orderId, HttpSession session) throws IOException, TasteException {
        Order order = orderService.get(orderId);
        // 订单状态待发货
        order.setStatus(OrderService.WAIT_DELIVERY);
        order.setPayDate(new Date());
        orderService.update(order);

        // 推荐书籍 -> 根据历史浏览记录推荐
        // 根据历史浏览记录推荐书籍的 id 集合, 注意不同的书籍推荐出来的书可能存在重复,
        // 这里用 LinkedHashSet, 它可以保证插入的顺序, 也可以去重
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        // 记录推荐的书籍
        List<Book> historyList = new ArrayList<>();
        // 浏览痕迹的书籍从 session 中获取
        LinkedList<Integer> basedOnBrowsingHistoryIds = (LinkedList<Integer>) session.getAttribute("basedOnBrowsingHistory");
        // 遍历得到的书籍
        if (basedOnBrowsingHistoryIds != null && !basedOnBrowsingHistoryIds.isEmpty()) {
            begin:
            for (int bookId : basedOnBrowsingHistoryIds) {
                // 书籍必须存在评论, 否则不予推荐
                boolean bookHasComments = scoreService.getNumByBookId(bookId);
                if (bookHasComments) {
                    // 计算其相似书籍
                    long[] bookIdList = myItemBasedRecommender.myItem(Long.parseLong(bookId + ""));
                    for (long id : bookIdList) {
                        set.add((int) id);
                        // 最多推荐 50 本
                        if (set.size() >= 50) {
                            break begin;
                        }
                    }
                }
            }
            for (Integer id : set) {
                Book book = bookService.get(id);
                historyList.add(book);
            }
        }
        PayedVo payedVo = new PayedVo();
        payedVo.setOrder(order);
        payedVo.setBasedOnBrowsingHistoryBooks(historyList);
        return Result.success(payedVo);
    }


    /**
     * 评价书籍
     *
     * @param bookId
     * @param orderId
     * @return
     */
    @RequestMapping("/review")
    public Result<ReviewVo> review(Integer bookId, Integer orderId) {
        Book book = bookService.get(bookId);
        Order order = orderService.get(orderId);
        if (order == null || book == null) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        ReviewVo reviewVo = new ReviewVo();
        reviewVo.setOrder(order);
        reviewVo.setBook(book);
        return Result.success(reviewVo);
    }

    /**
     * 提交评价
     *
     * @param session
     * @param orderId
     * @param bookId
     * @param score
     * @param content
     * @return
     * @throws IOException
     */
    @RequestMapping("/doReview")
    public Result<List<Comment>> doReview(HttpSession session,
                                          Integer orderId,
                                          Integer bookId,
                                          Integer score,
                                          String content) throws IOException {
        // 修改订单状态为完成
        Order order = orderService.get(orderId);
        order.setStatus(OrderService.FINISH);
        orderService.update(order);
        // 新增评论
        User user = (User) session.getAttribute("user");
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBookid(bookId);
        comment.setPublicDate(String.valueOf(new Date()));
        comment.setUserid(user.getId());
        comment.setCommentScore(score);

        int ret = commentService.add(comment);
        if (ret == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        // 更新评分数据库
        int ret1 = scoreService.addScore(user.getId(), bookId, score);
        if (ret1 == 0) {
            return Result.error(ErrorCode.DATABASE_ERROR);
        }
        // 提交评价后, 可以看到所有人的评论信息
        List<Comment> comments = commentService.listByBookId(bookId);
        return Result.success(comments);
    }

    /**
     * 搜索功能
     *
     * @param keyword 查询关键词
     * @return
     */
    @RequestMapping("/searchBooks")
    public Result<List<Book>> searchProduct(String keyword, Integer offset) {
        // offset, 偏移量, 从第一条开始查询
        List<Book> bookList = bookService.search(keyword, offset * 20);
        return Result.success(bookList);
    }
}
