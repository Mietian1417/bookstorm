package com.xiyu.demo.service.impl;

import com.xiyu.demo.mapper.BookMapper;
import com.xiyu.demo.pojo.Book;
import com.xiyu.demo.pojo.BookExample;
import com.xiyu.demo.pojo.Category;
import com.xiyu.demo.pojo.CategoryClass;
import com.xiyu.demo.service.*;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 09:39
 *
 * @author 陈子豪
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    CategoryClassService categoryClassService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CommentService commentService;

    @Autowired
    ScoreService scoreService;

    @Override
    public int add(Book book) {
        return bookMapper.insertSelective(book);
    }

    @Override
    public int delete(Integer id) {
        return bookMapper.deleteByPrimaryKey(id);

    }

    @Override
    public int update(Book book) {
        return bookMapper.updateByPrimaryKey(book);
    }

    @Override
    public Book get(Integer id) {
        return bookMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addCommentCount(Book book) {
        int num = book.getNumberOfPeople();
        book.setNumberOfPeople(num + 1);
        bookMapper.updateByPrimaryKey(book);
    }

    /**
     * 查询一个分类下的所有书籍
     * 书籍的分类信息存在 label 字段中, 这个字段可能包含很多信息, 比如书的作者, 书的发行地点, 书的突出点, 还有书的类别
     * 例如: 追风筝的人 ->  追风筝的人/人性/救赎/阿富汗/小说/外国文学/卡勒德·胡赛尼/成长/
     *
     * @param categoryClassId
     * @return
     */
    @Override
    public List<Book> list(Integer categoryClassId) {
        // 获取二级分类 id
        CategoryClass c = categoryClassService.get(categoryClassId);
        BookExample bookExample = new BookExample();
        // 二级分类名称条件查询
        String s = "%" + c.getCategoryClassname() + "%";
        bookExample.or().andLabelLike(s);
        return bookMapper.selectByExample(bookExample);
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories) {
            fill(category);
        }

    }

    @Override
    public void fill(Category category) {
        List<Book> books = list(category.getId());
        category.setBookList(books);
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int BookNumberOfEachRow = 8;
        for (Category category : categories) {
            List<Book> products = category.getBookList();
            List<List<Book>> productByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += BookNumberOfEachRow) {
                int size = i + BookNumberOfEachRow;
                size = size > products.size() ? products.size() : size;
                List<Book> productsOfEachRow = products.subList(i, size);
                productByRow.add(productsOfEachRow);
            }
            category.setBookByRow(productByRow);
        }

    }

    @Override
    public List<Book> search(String keyword, int offset) {
        BookExample example = new BookExample();

        example.or().andBookNameLike("%" + keyword + "%");
        example.or().andIsbnLike("%" + keyword + "%");
        example.or().andAuthorLike("%" + keyword + "%");
        example.or().andLabelLike("%" + keyword + "%");
        example.or().andAuthorIntroductionLike("%" + keyword + "%");
        example.or().andBookInfoLike("%" + keyword + "%");
        example.setLimit(20);
        example.setOffset(offset);
        example.setOrderByClause("id desc");
        return bookMapper.selectByExample(example);
    }

    @Override
    public List<Book> search(String keyword) {
        BookExample example = new BookExample();
        example.or().andBookNameLike("%" + keyword + "%");
        example.or().andIsbnLike("%" + keyword + "%");
        example.or().andAuthorLike("%" + keyword + "%");
        example.or().andLabelLike("%" + keyword + "%");
        example.or().andAuthorIntroductionLike("%" + keyword + "%");
        example.or().andBookInfoLike("%" + keyword + "%");
        example.setOrderByClause("id desc");
        return bookMapper.selectByExample(example);
    }


    /**
     * 查找指定分类下的书籍
     * @param keyword
     * @param categoryClassName
     * @return
     */
    @Override
    public List<Book> search(String keyword, String categoryClassName) {
        BookExample example = new BookExample();
        // 指定分类
        example.or().andLabelLike("%" + categoryClassName + "%");
        // 查询词匹配
        example.or().andBookNameLike("%" + keyword + "%");
        example.or().andIsbnLike("%" + keyword + "%");
        example.or().andAuthorLike("%" + keyword + "%");
        example.or().andLabelLike("%" + keyword + "%");
        example.or().andAuthorIntroductionLike("%" + keyword + "%");
        example.or().andBookInfoLike("%" + keyword + "%");
        example.setOrderByClause("id desc");
        return bookMapper.selectByExample(example);
    }

    /**
     * 新书速递 获取 10 本最新的书
     *
     * @return
     */
    @Override
    public List<Book> listBook() {
        BookExample bookExample = new BookExample();
        bookExample.setOrderByClause("date DESC");
        List<Book> list = bookMapper.selectByExample(bookExample);
        List<Book> bookList = new ArrayList<>();
        int i = 0;

        for (Book book : list) {
            if (i > 9) {
                break;
            }
            int id = book.getId();
            boolean bookHasComments = scoreService.getNumByBookId(id);
            if (bookHasComments) {
                bookList.add(book);
                i++;
            }

        }
        return bookList;
    }

    /**
     * 热门书籍 获取 10 本最热门的书
     *
     * @return
     */
    @Override
    public List<Book> listBookSale() {
        BookExample bookExample = new BookExample();
        bookExample.setOrderByClause("number_of_people DESC");
        List<Book> list = bookMapper.selectByExample(bookExample);
        List<Book> bookList = new ArrayList<>();
        int i = 0;
        for (int j = 0; j < list.size(); j++) {
            if (i > 9) {
                break;
            }
            int id = list.get(j).getId();
            // 热门的书籍必须存在评论, 否则不予推荐
            boolean bookHasComments = scoreService.getNumByBookId(id);
            if (bookHasComments) {
                bookList.add(list.get(j));
                i++;
            }

        }
        return bookList;
    }

    @Override
    public List<Book> listBook(String s) {
        BookExample bookExample = new BookExample();
        String s1 = "%" + s + "%";
        bookExample.or().andLabelLike(s1);
        return bookMapper.selectByExample(bookExample);
    }

    /**
     * 获取所有书籍
     *
     * @return
     */
    @Override
    public List<Book> listBookAll() {
        BookExample bookExample = new BookExample();
        return bookMapper.selectByExample(bookExample);
    }

    /**
     * 返回分类书籍信息, 默认销量降序查询
     *
     * @param categoryClassName 二级分类名称
     * @param queryWay          分类方式
     * @param offset            偏移量
     * @return
     */
    @Override
    public List<Book> getListBook(String categoryClassName, String queryWay, int offset) {
        BookExample bookExample = new BookExample();
        // 完全模糊匹配
        String s = "%" + categoryClassName + "%";
        bookExample.or().andLabelLike(s);
        // 每页个数
        bookExample.setLimit(20);
        bookExample.setOffset(offset);
        if ("priceAscending".equals(queryWay)) {
            // 价格升序
            bookExample.setOrderByClause("price ASC");
        } else if ("ratingDescending".equals(queryWay)) {
            // 评分降序
            bookExample.setOrderByClause("score DESC");
        } else if ("pageSort".equals(queryWay)) {
            // 分页排序
            bookExample.setOrderByClause("pages ASC");
        } else if ("commentsDescending".equals(queryWay)) {
            // 评论数降序
            bookExample.setOrderByClause("number_of_people DESC");
        } else if ("dateDescending".equals(queryWay)) {
            // 日期降序
            bookExample.setOrderByClause("date DESC");
        } else if ("salesDescending".equals(queryWay)) {
            // 销量降序
            bookExample.setOrderByClause("saleNumber DESC");
        }
        return bookMapper.selectByExample(bookExample);
    }

    /**
     * 获取一个分类的所有书籍数量
     *
     * @param categoryClassName
     * @return
     */
    @Override
    public long getBookCountByCategoryClass(String categoryClassName) {
        BookExample bookExample = new BookExample();
        String s = "%" + categoryClassName + "%";
        bookExample.or().andLabelLike(s);
        return bookMapper.countByExample(bookExample);
    }

    /**
     * 获取一个分类的所有书籍
     *
     * @param categoryClassName
     * @return
     */
    @Override
    public List<Book> getAllBooksByCategoryClass(String categoryClassName) {
        BookExample bookExample = new BookExample();
        String s = "%" + categoryClassName + "%";
        bookExample.or().andLabelLike(s);
        return bookMapper.selectByExample(bookExample);
    }

    /**
     * 获取一个分类下的所有书籍
     *
     * @param categoryName
     * @return
     */
    @Override
    public Integer getBookCountByCateGoryClass1(String categoryName) {
        BookExample bookExample = new BookExample();
        String s = "%" + categoryName + "%";
        bookExample.or().andLabelLike(s);
        List<Book> bookList = bookMapper.selectByExample(bookExample);
        List<Book> bookList1 = new ArrayList<>();
        for (Book book : bookList) {
            boolean bookHasComments = scoreService.getNumByBookId(book.getId());
            if (bookHasComments) {
                bookList1.add(book);
            }
        }
        return bookList.size();
    }

    /**
     * 将推荐项转化实际的书籍
     *
     * @param list
     * @return
     */
    @Override
    public List<Book> getRecommend(List<RecommendedItem> list) {
        List<Book> listBook = new ArrayList<>();
        for (RecommendedItem r : list) {
            // Math.toIntExact 将 long -> int
            // 精度不丢失就正常使用, 精度丢失或溢出就抛异常
            Integer id = Math.toIntExact(r.getItemID());
            Book book = get(id);
            listBook.add(book);
        }
        return listBook;
    }

    // 推荐项转书籍列表
    @Override
    public List<Book> listBookId(long[] b) {
        List<Book> list = new ArrayList<>();
        int i = 0;
        for (long l : b) {
            if (i >= 4) {
                break;
            }
            Book book = get(Math.toIntExact(l));
            list.add(book);
            i++;
        }
        return list;
    }

}
