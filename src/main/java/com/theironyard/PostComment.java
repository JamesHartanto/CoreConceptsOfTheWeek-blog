package com.theironyard;

/**
 * Created by JamesHartanto on 4/24/17.
 */
public class PostComment {
    Integer id;
    Person author;
    String text;
    String date;

    public PostComment() {
    }

    public PostComment(Integer id, Person author, String text, String date) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
