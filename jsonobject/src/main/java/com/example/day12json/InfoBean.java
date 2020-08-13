package com.example.day12json;

public class InfoBean {
    private String author;
    private String chapterName;
    private String niceShareDate;
    private String title;

    public InfoBean(String author, String chapterName, String niceShareDate, String title) {
        this.author = author;
        this.chapterName = chapterName;
        this.niceShareDate = niceShareDate;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getNiceShareDate() {
        return niceShareDate;
    }

    public void setNiceShareDate(String niceShareDate) {
        this.niceShareDate = niceShareDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
