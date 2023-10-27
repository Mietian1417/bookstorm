package com.xiyu.demo.pojo;

public class Comment {
    private Integer id;

    private String content;

    private Integer userid;

    private Integer bookid;

    private Integer commentScore;

    private String publicDate;

    /**
     * 为了展示评论的人
     */
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public Integer getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(Integer commentScore) {
        this.commentScore = commentScore;
    }

    public String getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(String publicDate) {
        this.publicDate = publicDate == null ? null : publicDate.trim();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userid=" + userid +
                ", bookid=" + bookid +
                ", commentScore='" + commentScore + '\'' +
                ", publicDate='" + publicDate + '\'' +
                ", user=" + user +
                '}';
    }
}