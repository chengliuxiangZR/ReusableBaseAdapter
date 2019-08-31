package com.example.asus.reusablebaseadapter;

public class Book {
    private String bName;
    private String bAuthor;

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbAuthor() {
        return bAuthor;
    }

    public void setbAuthor(String bAuthor) {
        this.bAuthor = bAuthor;
    }

    public Book(String bName, String bAuthor) {
        this.bName = bName;
        this.bAuthor = bAuthor;

    }
}
