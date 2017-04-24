package com.theironyard;

import java.util.List;

/**
 * Created by JamesHartanto on 4/21/17.
 */
public class Post {
    private Integer id;
    private Integer person_id;
    private String title;
    private String post;
    private String date;
    private List<PostComment> comments;


    public Post() {
    }

    public Post(Integer person_id, String title, String post, String date) {
        this.person_id = person_id;
        this.title = title;
        this.post = post;
        this.date = date;
    }

    public Post(Integer id, Integer person_id, String title, String post, String date) {
        this.id = id;
        this.person_id = person_id;
        this.title = title;
        this.post = post;
        this.date = date;
    }

    public Post(Integer id, Integer person_id, String title, String post, String date, List<PostComment> comments) {
        this.id = id;
        this.person_id = person_id;
        this.title = title;
        this.post = post;
        this.date = date;
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }
}
