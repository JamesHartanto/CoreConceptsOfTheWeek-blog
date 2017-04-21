package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by JamesHartanto on 4/21/17.
 */
@Component
public class BlogRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    // List blog posts
    public List<Blog> listBlogs(){
        return jdbcTemplate.query("SELECT * FROM blogs",
                (resultSet, i) -> new Blog(
                        resultSet.getInt("id"),
                        resultSet.getInt("person_id"),
                        resultSet.getString("blog"),
                        resultSet.getString("date")
                ));
    }

    // List a specific user's blogs
    public List<Blog> listMyBlogs(Integer userId) {
        return jdbcTemplate.query("SELECT * FROM blogs WHERE person_id=?",
                new Object[]{userId},
                (resultSet, i) -> new Blog(
                        resultSet.getInt("id"),
                        resultSet.getInt("person_id"),
                        resultSet.getString("blog"),
                        resultSet.getString("date")
                ));
    }

    // Add blog post
    public void addBlogPost(Blog blog){
        jdbcTemplate.update("INSERT INTO blogs(person_id, blog, date) VALUES (?,?,?)",
                new Object[]{blog.getPerson_id(), blog.getBlog(), blog.getDate()});
    }

    // Edit blog post
    public void editBlogPost(Blog blog){
        jdbcTemplate.update("UPDATE blogs SET blog = ?, date = ? WHERE id = ?",
                new Object[]{blog.getBlog(),blog.getDate(),blog.getId()});
    }

    // Delete blog post
    public void deleteBlogPost(Blog blog){
        jdbcTemplate.update("DELETE blogs WHERE id = ?",
                new Object[]{blog.getId()});
    }
}
