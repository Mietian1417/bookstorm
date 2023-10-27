package com.xiyu.demo.service.impl;

import com.xiyu.demo.service.MyItemBasedRecommender;
import com.xiyu.demo.service.ScoreService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 13:13
 *
 * @author 陈子豪
 */
@Service
public class MyItemBasedRecommenderImpl implements MyItemBasedRecommender {

    @Value("${personalizedRecommendations.scoreCsvFile}")
    private String scoreCsvFile;

    @Autowired
    ScoreService scoreService;

    @Override
    public List<RecommendedItem> myItemBasedRecommender(long userId, int size) {
        List<RecommendedItem> recommendations = null;
        try {
            File modelFile = new File(scoreCsvFile);
            // 构造数据模型
            DataModel model = new FileDataModel(modelFile);
            // 计算内容相似度
            ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
            // 构造推荐引擎
            Recommender recommender = new GenericItemBasedRecommender(model, similarity);
            // 得到推荐结果
            recommendations = recommender.recommend(userId, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recommendations;
    }


    /**
     * 计算相似书籍
     * @param itemId
     * @return
     * @throws TasteException
     * @throws IOException
     */
    @Override
    public long[] myItem(long itemId) throws TasteException, IOException {
        File modelFile = new File(scoreCsvFile);
        // 构造数据模型
        DataModel model = new FileDataModel(modelFile);
        // 计算内容相似度
        ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
        return similarity.allSimilarItemIDs(itemId);
    }
}
