package com.theironyard;

/**
 * Created by JamesHartanto on 4/22/17.
 */

// Just for the author name with the blog... for the homePage.html
public class BlogPerson {
    private Integer id;
    private Integer person_id;
    private String title;
    private String post;
    private String date;
    private String username;

    public BlogPerson(Integer id, Integer person_id, String title, String post, String date, String username) {
        this.id = id;
        this.person_id = person_id;
        this.title = title;
        this.post = post;
        this.date = date;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
