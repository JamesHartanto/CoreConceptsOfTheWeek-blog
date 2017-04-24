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

    // List blog posts with search parameter
    public List<Post> listBlogs(String search){
        return jdbcTemplate.query("SELECT * FROM blogs WHERE " +
                        "lower(title) LIKE lower(?) " +
                        "ORDER BY id DESC",
                new Object[]{"%" + search + "%"},
                (resultSet, i) -> new Post(
                        resultSet.getInt("id"),
                        resultSet.getInt("person_id"),
                        resultSet.getString("title"),
                        resultSet.getString("post"),
                        resultSet.getString("date")
                ));
    }

    // List the user's blog posts (by selecting username) - used in viewPersonBlogPosts
    public List<Post> selectPersonPosts(String username){
        return jdbcTemplate.query("SELECT * FROM blogs " +
                        "JOIN person ON person.id=blogs.person_id " +
                        "AND person.username = ?" +
                        "ORDER BY blogs.id DESC",
                new Object[]{username},
                (resultSet, i) -> new Post(
                        resultSet.getInt("id"),
                        resultSet.getInt("person_id"),
                        resultSet.getString("title"),
                        resultSet.getString("post"),
                        resultSet.getString("date")
                ));
    }

    // List the user's blog posts (by id) - used in viewMyBlogs
    public List<Post> listMyBlogs(Integer userId) {
        return jdbcTemplate.query("SELECT * FROM blogs WHERE person_id=? ORDER BY id DESC",
                new Object[]{userId},
                (resultSet, i) -> new Post(
                        resultSet.getInt("id"),
                        resultSet.getInt("person_id"),
                        resultSet.getString("title"),
                        resultSet.getString("post"),
                        resultSet.getString("date")
                ));
    }

    // Add post post
    public void addBlogPost(Post post){
        jdbcTemplate.update("INSERT INTO blogs(person_id, title, post, date) VALUES (?,?,?,?)",
                new Object[]{post.getPerson_id(), post.getTitle(), post.getPost(), post.getDate()});
    }

    // Edit post post
    public void editBlogPost(Post post){
        jdbcTemplate.update("UPDATE blogs SET title=?, post = ?, date = ? WHERE id = ?",
                new Object[]{post.getTitle(), post.getPost(), post.getDate(), post.getId()});
    }

    // Delete blog post
    public void deleteBlogPost(Integer PostId){
        jdbcTemplate.update("DELETE FROM blogs WHERE id = ?",
                new Object[]{PostId});
    }

    // View a particular blog
    public Post viewABlog(Integer blogId) {
        return jdbcTemplate.queryForObject("SELECT * FROM blogs WHERE id = ?",
                new Object[]{blogId},
                (resultSet, i) -> new Post(
                        resultSet.getInt("id"),
                        resultSet.getInt("person_id"),
                        resultSet.getString("title"),
                        resultSet.getString("post"),
                        resultSet.getString("date")
                ));
    }


    // COMMENTS RELATED TO THE BLOG POSTS
    // Get the comments from a particular post
    public List<PostComment> listOfPostComments(Integer post_id){
        return jdbcTemplate.query("SELECT c.*,p.* FROM comment as c " +
                        "JOIN person as p " +
                        "ON c.author_id=p.id " +
                        "WHERE post_id = ?",
                new Object[]{post_id},
                (resultSet, i) -> new PostComment(
                        resultSet.getInt("id"),
                        resultSet.getString("text"),
                        new BlogPerson(resultSet.getInt("author_id"),
                                resultSet.getString("date"),
                                )
                ));
    }

    // Getting the blogPerson for listOfPostComments
    public BlogPerson infoForlistComments(Integer post_id){
        return jdbcTemplate.query("SELECT c.*,p.* FROM comment as c " +
                "JOIN person as p " +
                "ON c.author_id=p.id " +
                "WHERE post_id = ?",
                new Object[]{post_id},
                (resultSet, i) -> new BlogPerson(post_id,
                        resultSet.getInt("author_id"),
                        resultSet.getString("date"),
                        resultSet.getString("username")));
    }

    // Adding comment
    public void addComment(Integer post_id, Integer author_id, String text, String date){
        jdbcTemplate.update("INSERT INTO comment(post_id,author_id,text,date) VALUES (?,?,?,?)",
                new Object[]{post_id,author_id,text,date});
    }

    // Editing comment from id of the comment
    public void editComment(Integer id, String text, String date){
        jdbcTemplate.update("UPDATE comment SET text=?, date=? WHERE id=?",
                new Object[]{text,date,id});
    }
}
