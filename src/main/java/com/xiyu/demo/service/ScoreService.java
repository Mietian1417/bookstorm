package com.xiyu.demo.service;


import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.ScoreBook;

import java.io.IOException;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 15:55
 *
 * @author 陈子豪
 */
public interface ScoreService {

    /**
     * 根据分数, 获得该分数的打分人数
     * @param score
     * @return
     */
    long getPeopleByScore(int score);

    /**
     * 获取所有图书的评分情况, 从低到高
     * @return
     */
    long[] getNum();

    /**
     * 添加评分
     * @param userId
     * @param nookId
     * @param score
     * @return
     */
    Integer addScore(Integer userId, Integer nookId, Integer score);

    /**
     * 获取一本书的所有评分, 从低到高排列
     * @param bookId
     * @return
     */
    long[] getAllScoreByBook(Integer bookId);

    /**
     * 获取一个分类的的所有评分, 从低到高排列
     * @param categoryClassName 该分类的名称
     * @return
     */
    long[] getAllScoreByCategoryClass(String categoryClassName);
    long[] getAllScoreByCategoryClass(List<Book> bookList);

    /**
     * 一本书是否存在评分
     * @param bookId
     * @return
     */
    boolean getNumByBookId(Integer bookId);

    /**
     * 获取一本书的所有评分, 评分从高到底排列
     * @param bookId
     * @return
     */
    List<ScoreBook> listByBook(Integer bookId);

    /**
     * 获取一个人对所有书籍的评分列表, 评分从高到低排列
     * @param userId
     * @return
     */
    List<ScoreBook> listByUser(Integer userId, Integer limit);

    /**
     * 更新评分文件
     * @param userId
     * @param bookId
     * @param score
     * @throws IOException
     */
    void updateScoreFile(Integer userId, Integer bookId, Integer score) throws IOException;


}
