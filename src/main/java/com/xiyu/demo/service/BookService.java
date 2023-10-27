package com.xiyu.demo.service;


import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.Category;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 09:30
 *
 * @author 陈子豪
 */
public interface BookService {
    /**
     * 增加一条数据
     *
     * @param book
     */
    int add(Book book);

    /**
     * 通过id删除一条数据
     *
     * @param id
     */
    int delete(Integer id);

    /**
     * 更新一条数据
     *
     * @param book
     */
    int update(Book book);

    /**
     * 根据id获取一条数据
     *
     * @param id
     * @return
     */
    Book get(Integer id);

    /**
     * 根据 categoryId 返回所有对应分类的书籍
     *
     * @param categoryId
     * @return
     */
    List<Book> list(Integer categoryId);

    /**
     * 书的评论 + 1
     *
     * @param book
     */
    void addCommentCount(Book book);

    /**
     * 为多个分类填充产品集合
     *
     * @param categories
     */
    void fill(List<Category> categories);

    /**
     * 为一个分类填充产品集合
     *
     * @param category
     */
    void fill(Category category);

    /**
     * 为多个分类填充产品集合
     *
     * @param categories
     */
    void fillByRow(List<Category> categories);

    /**
     * 为产品填充ReviewCount字段
     *
     * @param product
     */
    //void setReviewCount(Product product);

    /**
     * 根据keyword返回相应的产品集合
     *
     * @param keyword
     * @return
     */
    List<Book> search(String keyword, int offset);

    List<Book> search(String keyword);

    /**
     * 查找指定分类下的书籍
     * @param keyword
     * @return
     */
    List<Book> search(String keyword, String categoryClassName);

    /**
     * 挑选出最新的五本书
     *
     * @return
     */
    List<Book> listBook();


    /**
     * 书籍单条件查询
     *
     * @param s
     * @return
     */
    List<Book> listBook(String s);

    /**
     * 挑选出销量最好的 10 本书
     *
     * @return
     */
    List<Book> listBookSale();

    /**
     * 获取所有书籍
     *
     * @return
     */
    List<Book> listBookAll();

    /**
     * 查询一个分类的所有书籍, 按查询方式查询(销量, 评分, 价格...)
     *
     * @param categoryClassName
     * @param queryWay
     * @param offset
     * @return
     */
    List<Book> getListBook(String categoryClassName, String queryWay, int offset);


    /**
     * 获取一个分类的所有书籍数量
     *
     * @param categoryClassName
     * @return
     */
    long getBookCountByCategoryClass(String categoryClassName);

    /**
     * 获取一个分类的所有书籍
     *
     * @param categoryClassName
     * @return
     */
    List<Book> getAllBooksByCategoryClass(String categoryClassName);

    /**
     * 获取一个分类的所有书籍数量
     *
     * @param categoryClassName
     * @return
     */
    Integer getBookCountByCateGoryClass1(String categoryClassName);

    /**
     * 将推荐项转化实际的书籍
     *
     * @param list
     * @return
     */
    List<Book> getRecommend(List<RecommendedItem> list);

    List<Book> listBookId(long[] b);
}
