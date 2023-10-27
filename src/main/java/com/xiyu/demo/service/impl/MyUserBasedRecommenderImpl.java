package com.xiyu.demo.service.impl;

import com.xiyu.demo.pojo.ScoreBook;
import com.xiyu.demo.service.MyUserBasedRecommender1;
import com.xiyu.demo.service.ScoreService;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 推荐书籍
 */
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 13:55
 *
 * @author 陈子豪
 */
@Service
public class MyUserBasedRecommenderImpl implements MyUserBasedRecommender1 {

    @Value("${personalizedRecommendations.scoreCsvFile}")
    private String scoreCsvFile;

    @Autowired
    ScoreService scoreService;

    /**
     * 推荐功能 猜你喜欢
     * @param userId 推荐的用户
     * @param size  推荐的书本数量
     * @return
     */
    @Override
    public List<RecommendedItem> userBasedRecommender(long userId, int size) {
        // step:1 构建模型 2 计算相似度 3 查找 k 紧邻 4 构造推荐引擎
        List<RecommendedItem> recommendations = null;
        try {
            System.out.println(scoreCsvFile);
            System.out.println(scoreCsvFile);
            System.out.println(scoreCsvFile);
            System.out.println(scoreCsvFile);
            File modelFile = new File(scoreCsvFile);
            // 构造数据模型
            DataModel model = new FileDataModel(modelFile);
            // 用PearsonCorrelation 算法计算用户相似度
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            // 计算用户的“邻居”，这里将与该用户最近距离为 3 的用户设置为该用户的“邻居”。
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
            // 采用 CachingRecommender 为 RecommendationItem 进行缓存
            Recommender recommender = new CachingRecommender(new GenericUserBasedRecommender(model, neighborhood, similarity));
            // 得到推荐的结果，size是推荐结果的数目
            recommendations = recommender.recommend(userId, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recommendations;
    }


    /**
     * 推荐功能 买过该书的人还喜欢
     *      查询出买过该书的用户, 在查询出这些用户评分最高的五本书, 在统一这些书返回
     * @param userId
     * @param bookId
     * @return
     */
    @Override
    public Set<Integer> buyByUser(Integer userId, Integer bookId) {
        // 返回的推荐书籍 id
        Set<Integer> bookIdBypass = new HashSet<>();
        ArrayList<Integer> bookIds = new ArrayList<>();
        try {
            // 获取这本书的所有评分信息, 从高到低排列
            List<ScoreBook> scoreBookList = scoreService.listByBook(bookId);
            // 遍历所有评论过该书的用户 挑选出最相似的五个用户
            for (ScoreBook scoreBook : scoreBookList) {
                // 获取当前用户对所有书籍的评分列表, 评分从高到低排列
                // 只查询五个最高的评分, 查询 6 个是因为可能包含当前的书籍
                List<ScoreBook> scoreBookListByUser = scoreService.listByUser(scoreBook.getUserId(), 6);
                // 遍历当前用户的所有评分 挑选出每个用户买过的五本评分最高的书
                int count = 0;
                for (ScoreBook curScoreBook : scoreBookListByUser) {
                    if (curScoreBook.getBookId().equals(bookId)) {
                        continue;
                    }
                    if(count++ > 4){
                        break;
                    }
                    bookIds.add(curScoreBook.getBookId());
                    bookIdBypass.add(curScoreBook.getBookId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookIdBypass;
    }
}