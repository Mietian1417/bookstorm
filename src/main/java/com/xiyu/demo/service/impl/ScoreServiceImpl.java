package com.xiyu.demo.service.impl;

import com.xiyu.demo.mapper.ScoreBookMapper;
import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.ScoreBook;
import com.xiyu.demo.pojo.ScoreBookExample;
import com.xiyu.demo.service.BookService;
import com.xiyu.demo.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 16:08
 *
 * @author 陈子豪
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    ScoreBookMapper scoreBookMapper;

    /**
     * 根据分数获得该分数打分人数, 针对所有图书
     *
     * @param score
     * @return
     */
    @Override
    public long getPeopleByScore(int score) {
        ScoreBookExample scoreBookExample = new ScoreBookExample();
        scoreBookExample.or().andScoreEqualTo(score);
        return scoreBookMapper.countByExample(scoreBookExample);
    }

    /**
     * 获取所有图书的评分情况, 从低到高
     *
     * @return
     */
    @Override
    public long[] getNum() {
        ScoreBookExample scoreBookExample = new ScoreBookExample();
        long couts[] = new long[5];
        for (int i = 1; i < 6; i++) {
            scoreBookExample.or().andScoreEqualTo(i);
            long cout = scoreBookMapper.countByExample(scoreBookExample);
            couts[i - 1] = cout;
            // 清空之前的设置, 这非常重要, 要不然查询条件会一直 or 叠加!
            scoreBookExample.clear();
        }
        return couts;
    }


    /**
     * 获取一本书的所有评分, 从低到高
     *
     * @param bookId
     * @return
     */
    @Override
    public long[] getAllScoreByBook(Integer bookId) {
        ScoreBookExample scoreBookExample = new ScoreBookExample();
        long[] scores = new long[5];
        for (int i = 1; i < 6; i++) {
            scoreBookExample.or().andBookIdEqualTo(bookId).andScoreEqualTo(i);
            long count = scoreBookMapper.countByExample(scoreBookExample);
            scores[i - 1] = count;
            // 清空之前的设置, 这非常重要, 要不然查询条件会一直 or 叠加!
            scoreBookExample.clear();
        }
        return scores;
    }

    /**
     * 获取一个分类的的所有评分, 从低到高
     *
     * @param categoryClassName 该分类的名称
     * @return
     */
    @Override
    public long[] getAllScoreByCategoryClass(String categoryClassName) {
        long start = System.currentTimeMillis();
        long[] scores = {0, 0, 0, 0, 0};
        // List<Book> categoryClassBooks = bookService.getAllBooksByCategoryClass(categoryClassName);
        // // 创建一个线程池
        // ExecutorService threadPool = Executors.newFixedThreadPool(6);
        // List<Future<long[]>> futures = new ArrayList<>();
        // for (Book book : categoryClassBooks) {
        //     // 使用线程池执行查询任务
        //     Future<long[]> future = threadPool.submit(new Callable<long[]>() {
        //         @Override
        //         public long[] call() {
        //             // 返回当前这本书的评分
        //             return getAllScoreByBook(book.getId());
        //         }
        //     });
        //     futures.add(future);
        // }
        //
        // // 等待所有线程完成并合并结果
        // for (Future<long[]> future : futures) {
        //     try {
        //         long[] curBookScores = future.get();
        //         for (int i = 0; i < 5; i++) {
        //             scores[i] += curBookScores[i];
        //         }
        //     } catch (InterruptedException | ExecutionException e) {
        //         // 处理异常
        //     }
        // }
        //
        // // 关闭线程池
        // threadPool.shutdown();
        //
        // long end = System.currentTimeMillis();
        // System.out.println("查找" + categoryClassName + "分类书籍的耗时为: " + (end - start));
        return scores;
    }

    @Override
    public long[] getAllScoreByCategoryClass(List<Book> bookList) {
        long[] scores = {0, 0, 0, 0, 0};
        for (int i = 0; i < 5; i++) {
            scores[i] = (long) (Math.random() * 301) + 200;
        }
        return scores;
    }


    @Override
    public List<ScoreBook> listByBook(Integer bookId) {
        System.out.println("番薯多喝水色斑的缴纳圣诞节啊8***************************************************");
        ScoreBookExample scoreBookExample = new ScoreBookExample();
        scoreBookExample.or().andBookIdEqualTo(bookId);
        scoreBookExample.setOrderByClause("score DESC");
        System.out.println("番薯多喝水色斑的缴纳圣诞节啊8***************************************************");
        return scoreBookMapper.selectByExample(scoreBookExample);
    }

    /**
     * 获取一个人对所有书籍的评分列表, 评分从高到低排列
     *
     * @param userId
     * @return
     */
    @Override
    public List<ScoreBook> listByUser(Integer userId, Integer limit) {
        ScoreBookExample scoreBookExample = new ScoreBookExample();
        scoreBookExample.or().andUserIdEqualTo(userId);
        scoreBookExample.setOrderByClause("score DESC");
        scoreBookExample.setLimit(limit);
        scoreBookExample.setOffset(0);
        return scoreBookMapper.selectByExample(scoreBookExample);
    }

    /**
     * 书籍是否存在评分
     *
     * @param bookId
     * @return
     */
    @Override
    public boolean getNumByBookId(Integer bookId) {
        ScoreBookExample scoreBookExample = new ScoreBookExample();
        scoreBookExample.or().andBookIdEqualTo(bookId);
        List<ScoreBook> scoreBookList = scoreBookMapper.selectByExample(scoreBookExample);
        return scoreBookList != null && scoreBookList.size() != 0;
    }

    @Override
    public Integer addScore(Integer userId, Integer bookId, Integer score) {
        ScoreBook scoreBook = new ScoreBook();
        scoreBook.setBookId(bookId);
        scoreBook.setUserId(userId);
        scoreBook.setScore(score);
        // 同步更新 csv 文件
        updateScoreFile(userId, bookId, score);
        return scoreBookMapper.insertSelective(scoreBook);
    }

    @Override
    public void updateScoreFile(Integer userId, Integer bookId, Integer score) {
        // CSV 评分文件
        // File scoreFile = new File("DD:\\编程语言\\Java\\毕业设计\\BookRecommend-jsp\\BookRecommend\\bookStore\\ratings.csv");
        File scoreFile = new File(".\\ratings.csv");

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(scoreFile, true));
            // 添加新的数据行, 并换行
            bw.write(userId + "," + bookId + "," + score + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println();
            System.out.println();
            System.out.println("文件不存在!");
            System.out.println();
            System.out.println();
        }

    }
}
