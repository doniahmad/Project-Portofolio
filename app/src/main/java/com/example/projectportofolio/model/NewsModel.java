package com.example.projectportofolio.model;

public class NewsModel {

    private String newsTitle,newsDesc,newsImg,newsUrl,newsAuthor,newsPublish,newsContent;

    public NewsModel(String newsTitle,String newsDesc,String newsImg,String newsUrl,String newsAuthor,String newsPublish,String newsContent){
        this.newsTitle = newsTitle;
        this.newsDesc = newsDesc;
        this.newsImg = newsImg;
        this.newsUrl = newsUrl;
        this.newsAuthor = newsAuthor;
        this.newsPublish = newsPublish;
        this.newsContent = newsContent;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsPublish() {
        return newsPublish;
    }

    public void setNewsPublish(String newsPublish) {
        this.newsPublish = newsPublish;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }

    public String getNewsImg() {
        return newsImg;
    }

    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
    }
}
