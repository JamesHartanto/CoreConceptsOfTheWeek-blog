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
    public List<Blog> listBlogs(String search){
        return jdbcTemplate.query("SELECT * FROM blogs ORDER BY id DESC WHERE " +
                        "lower(title) LIKE lower(?)",
                new Object[]{"%" + search + "%"},
                (resultSet, i) -> new Blog(
                        resultSet.getInt("id"),
                        resultSet.getInt("person_id"),
                        resultSet.getString("title"),
                        resultSet.getString("post"),
                        resultSet.getString("date")
                ));
    }
//
//    // List MORE blog posts
//    public List<Blog> listMoreBlogs(){
//        return jdbcTemplate.query("SELECT * FROM blogs ORDER BY id DESC LIMIT 50",
//                (resultSet, i) -> new Blog(
//                        resultSet.getInt("id"),
//                        resultSet.getInt("person_id"),
//                        resultSet.getString("title"),
//                        resultSet.getString("post"),
//                        resultSet.getString("date")
//                ));
//    }

    // List a specific user's blogs
    public List<Blog> listMyBlogs(Integer userId) {
        return jdbcTemplate.query("SELECT * FROM blogs WHERE person_id=? ORDER BY id DESC",
                new Object[]{userId},
                (resultSet, i) -> new Blog(
                        resultSet.getInt("id"),
                        resultSet.getInt("person_id"),
                        resultSet.getString("title"),
                        resultSet.getString("post"),
                        resultSet.getString("date")
                ));
    }

    // Add blog post
    public void addBlogPost(Blog blog){
        jdbcTemplate.update("INSERT INTO blogs(person_id, title, post, date) VALUES (?,?,?,?)",
                new Object[]{blog.getPerson_id(), blog.getTitle(), blog.getPost(), blog.getDate()});
    }

    // Edit blog post
    public void editBlogPost(Blog blog){
        jdbcTemplate.update("UPDATE blogs SET title=? blog = ?, date = ? WHERE id = ?",
                new Object[]{blog.getTitle(), blog.getPost(),blog.getDate(),blog.getId()});
    }

    // Delete blog post
    public void deleteBlogPost(Blog blog){
        jdbcTemplate.update("DELETE blogs WHERE id = ?",
                new Object[]{blog.getId()});
    }

    // View a particular blog
    public Blog viewABlog(Integer blogId) {
        return jdbcTemplate.queryForObject("SELECT * FROM blogs WHERE id = ?",
                new Object[]{blogId},
                (resultSet, i) -> new Blog(
                        resultSet.getInt("id"),
                        resultSet.getInt("person_id"),
                        resultSet.getString("title"),
                        resultSet.getString("post"),
                        resultSet.getString("date")
                ));
    }
}
