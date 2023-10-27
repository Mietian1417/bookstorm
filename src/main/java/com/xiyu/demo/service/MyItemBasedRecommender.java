package com.xiyu.demo.service;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.IOException;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 13:05
 *
 * @author 陈子豪
 */
public interface MyItemBasedRecommender {
    List<RecommendedItem> myItemBasedRecommender(long userId, int size);

    /**
     * 计算相似书籍
     * @param ItemId
     * @return
     * @throws TasteException
     * @throws IOException
     */
    long[] myItem(long ItemId) throws TasteException, IOException;
}
