package com.xiyu.demo.pojo;

public class Book {
    private Integer id;

    private String bookName;

    private String author;

    private String publicSource;

    // 翻译的人
    private String translator;

    private String date;

    private Integer pages;

    private String isbn;

    private Float price;

    private String currentPrice;

    private String score;

    // 评价的人数
    private Integer numberOfPeople;

    private String bookInfo;

    private String authorIntroduction;

    // 目录
    private String catalog;

    private String label;

    // 书的 url
    private String image;

    // 销量
    private Integer saleNumber;

    // 库存
    private Integer storeNumber;

    // 所属类别
    private Integer classId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getpublicSource() {
        return publicSource;
    }

    public void setpublicSource(String publicSource) {
        this.publicSource = publicSource == null ? null : publicSource.trim();
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator == null ? null : translator.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn == null ? null : isbn.trim();
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice == null ? null : currentPrice.trim();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(String bookInfo) {
        this.bookInfo = bookInfo == null ? null : bookInfo.trim();
    }

    public String getAuthorIntroduction() {
        return authorIntroduction;
    }

    public void setAuthorIntroduction(String authorIntroduction) {
        this.authorIntroduction = authorIntroduction == null ? null : authorIntroduction.trim();
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog == null ? null : catalog.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Integer getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Integer saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Integer getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(Integer storeNumber) {
        this.storeNumber = storeNumber;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", publicSource='" + publicSource + '\'' +
                ", translator='" + translator + '\'' +
                ", date='" + date + '\'' +
                ", pages=" + pages +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", currentPrice='" + currentPrice + '\'' +
                ", score='" + score + '\'' +
                ", numberOfPeople=" + numberOfPeople +
                ", bookInfo='" + bookInfo + '\'' +
                ", authorIntroduction='" + authorIntroduction + '\'' +
                ", catalog='" + catalog + '\'' +
                ", label='" + label + '\'' +
                ", image='" + image + '\'' +
                ", saleNumber=" + saleNumber +
                ", storeNumber=" + storeNumber +
                ", classId=" + classId +
                '}';
    }
}