package com.theironyard;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by JamesHartanto on 4/21/17.
 */
public class Blog {
    private Integer id;
    private Integer person_id;
    private String blog;
    private String date;

    public Blog() {
    }

    public Blog(Integer person_id, String blog, String date) {
        this.person_id = person_id;
        this.blog = blog;
        this.date = date;
    }

    public Blog(Integer id, Integer person_id, String blog, String date) {
        this.id = id;
        this.person_id = person_id;
        this.blog = blog;
        this.date = date;
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

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
