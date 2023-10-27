package com.xiyu.demo.service;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;
import java.util.Set;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 13:47
 *
 * @author 陈子豪
 */
public interface MyUserBasedRecommender1 {

    /**
     * 推荐功能 猜你喜欢
     * @param userId 推荐的用户
     * @param size  推荐的书本数量
     * @return
     */
    List<RecommendedItem> userBasedRecommender(long userId, int size);

    /**
     * 推荐功能 买过该书的人还喜欢
     *      查询出买过该书的用户, 在查询出这些用户评分最高的五本书, 在统一这些书返回
     * @param userId
     * @param bookId
     * @return
     */
    Set<Integer> buyByUser(Integer userId, Integer bookId);
}
