package com.example.activejava.entries;

import java.io.Serializable;

/**
 * @author LinYongJin
 * @date 2019/9/7 16:15
 */
public class News implements Serializable {

    private String title;

    private String content;

    public News() {

    }

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
