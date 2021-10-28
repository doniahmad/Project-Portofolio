package com.example.projectportofolio.model;

public class NewsModel {

    private String newsTitle,newsDesc,newsImg,newsUrl;

    public NewsModel(String newsTitle,String newsDesc,String newsImg,String newsUrl){
        this.newsTitle = newsTitle;
        this.newsDesc = newsDesc;
        this.newsImg = newsImg;
        this.newsUrl = newsUrl;
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
