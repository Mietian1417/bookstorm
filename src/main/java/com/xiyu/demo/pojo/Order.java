package com.xiyu.demo.pojo;

import java.util.Date;
import java.util.List;

/**
 * 订单主表
 */
public class Order {
    // 主键
    private Integer id;
    // 订单号
    private String orderCode;
    // 收货地址
    private String address;
    // 邮编
    private String post;
    // 收货人
    private String receiver;
    // 手机号码
    private String mobile;
    // 用户在订单上备注的信息
    private String userMessage;
    // 订单创建时间
    private Date createDate;
    // 订单支付时间
    private Date payDate;
    // 发货日期
    private Date deliveryDate;
    // 确认收货日期
    private Date confirmDate;
    // 订单对应的用户id
    private Integer userId;
    // 订单的状态(待支付,待发货,待收货,待评论,已完成)
    // "waitPay";"waitDelivery";"waitConfirm";
    // "waitReview";"finish";"delete";
    private String status;
    // 收货地址id
    private Integer addressId;

    // 一对多, 一个订单可以有多个订单项
    private List<OrderItem> orderItems;

    // 下面的属性都是为了属性赋回, 这样一个对象就可以操作多个值, 不需要传递多个对象到前端
    private User user;

    private float total;

    private int totalNumber;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage == null ? null : userMessage.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "OrderVo{" +
                "id=" + id +
                ", orderCode='" + orderCode + '\'' +
                ", address='" + address + '\'' +
                ", post='" + post + '\'' +
                ", receiver='" + receiver + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", createDate=" + createDate +
                ", payDate=" + payDate +
                ", deliveryDate=" + deliveryDate +
                ", confirmDate=" + confirmDate +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                ", addressId=" + addressId +
                ", orderItems=" + orderItems +
                ", user=" + user +
                ", total=" + total +
                ", totalNumber=" + totalNumber +
                '}';
    }
}