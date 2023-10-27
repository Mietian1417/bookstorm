package com.xiyu.demo.service.impl;

import com.xiyu.demo.mapper.CommentMapper;
import com.xiyu.demo.pojo.Comment;
import com.xiyu.demo.pojo.CommentExample;
import com.xiyu.demo.pojo.User;
import com.xiyu.demo.service.CommentService;
import com.xiyu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-02
 * Time: 12:33
 *
 * @author 陈子豪
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    UserService userService;

    @Autowired
    CommentMapper commentMapper;


    @Override
    public List<Comment> listByBookId(Integer bookId) {
        CommentExample commentExample = new CommentExample();
        commentExample.or().andBookidEqualTo(bookId);
        commentExample.setOrderByClause("id desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        // 用户信息回填
        setUser(comments);
        return comments;
    }

    public void setUser(List<Comment> comments) {
        for (Comment comment : comments) {
            setUser(comment);
        }
    }

    private void setUser(Comment comment) {
        int userId = comment.getUserid();
        User user = userService.get(userId);
        comment.setUser(user);
    }

    @Override
    public int getCount(int bookId) {
        return listByBookId(bookId).size();
    }

    @Override
    public int add(Comment comment) {
        return commentMapper.insertSelective(comment);
    }

    @Override
    public List<Comment> getAllComments(){
        CommentExample commentExample = new CommentExample();
        commentExample.or().getAllCriteria();
        return commentMapper.selectByExample(commentExample);
    }

}
