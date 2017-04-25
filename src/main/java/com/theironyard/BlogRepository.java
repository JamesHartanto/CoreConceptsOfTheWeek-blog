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

    // List of blog posts with search parameter
    public List<Post> listPosts(String search){
        return jdbcTemplate.query("SELECT b.*, p.id_person, p.username " +
                        "FROM blogs as b JOIN person as p ON b.person_id = p.id_person " +
                        "WHERE lower(b.title) LIKE lower(?) " +
                        "ORDER BY b.id_post DESC",
                new Object[]{"%" + search + "%"},
                (resultSet, i) -> new Post(
                        resultSet.getInt("id_post"),
                        new Person(resultSet.getInt("person_id"),
                                resultSet.getString("username")),
                        resultSet.getString("title"),
                        resultSet.getString("post"),
                        resultSet.getString("date_post"),
                        listOfComments(resultSet.getInt("id_post"))));
    }

    //list of comments
    public List<PostComment> listOfComments(Integer post_id){
        return jdbcTemplate.query("SELECT b.id_post, b.person_id, b.title, p.username, c.* FROM blogs as b " +
                "JOIN comment as c ON c.post_id = b.id_post " +
                "JOIN person as p ON c.author_id = p.id_person " +
                "WHERE b.id_post = ?",
                new Object[]{post_id},
                (resultSet, i) -> new PostComment(
                        resultSet.getInt("id_comment"),
                        new Person(resultSet.getInt("author_id"),
                                resultSet.getString("username")),
                        resultSet.getString("text"),
                        resultSet.getString("date_comment")));
    }

    // List the user's blog posts (by selecting username) - used in viewPersonBlogPosts
    public List<Post> selectPersonPosts(String username){
        return jdbcTemplate.query("SELECT b.*, p.* FROM blogs as b " +
                        "JOIN person as p ON p.id_person=b.person_id AND p.username=? " +
                        "ORDER BY b.id_post DESC;",
                new Object[]{username},
                (resultSet, i) -> new Post(
                        resultSet.getInt("id_post"),
                        new Person(resultSet.getInt("id_person"),
                                resultSet.getString("username")),
                        resultSet.getString("title"),
                        resultSet.getString("post"),
                        resultSet.getString("date_post"),
                        listOfComments(resultSet.getInt("id_post"))));
    }

    // List the user's blog posts (by id) - used in viewMyBlogs
    public List<Post> listMyBlogs(Integer userId) {
        return jdbcTemplate.query("SELECT b.*, p.* FROM blogs as b " +
                        "JOIN person as p ON b.person_id = p.id_person " +
                        "WHERE person_id=? " +
                        "ORDER BY id_post DESC;",
                new Object[]{userId},
                (resultSet, i) -> new Post(
                        resultSet.getInt("id_post"),
                        new Person(resultSet.getInt("person_id"),
                                resultSet.getString("username")),
                        resultSet.getString("title"),
                        resultSet.getString("post"),
                        resultSet.getString("date_post"),
                        listOfComments(resultSet.getInt("id_post"))));
    }

    // Add post post
    public void addBlogPost(Post post){
        jdbcTemplate.update("INSERT INTO blogs(person_id, title, post, date_post) VALUES (?,?,?,?)",
                new Object[]{post.getAuthor().getId(), post.getTitle(), post.getPost(), post.getDate()});
    }

    // Edit post post
    public void editBlogPost(Post post){
        jdbcTemplate.update("UPDATE blogs SET title=?, post = ?, date_post = ? WHERE id_post = ?",
                new Object[]{post.getTitle(), post.getPost(), post.getDate(), post.getId()});
    }

    // Delete blog post
    public void deleteBlogPost(Integer PostId){
        jdbcTemplate.update("DELETE FROM blogs WHERE id_post = ?",
                new Object[]{PostId});
    }

    // View a particular post
    public Post viewABlog(Integer postId) {
        return jdbcTemplate.queryForObject("SELECT b.*,p.* FROM blogs as b " +
                        "JOIN person as p ON b.person_id = p.id_person " +
                        "WHERE id_post = ?",
                new Object[]{postId},
                (resultSet, i) -> new Post(
                        resultSet.getInt("id_post"),
                        new Person(resultSet.getInt("person_id"),
                                resultSet.getString("username")),
                        resultSet.getString("title"),
                        resultSet.getString("post"),
                        resultSet.getString("date_post"),
                        listOfComments(resultSet.getInt("id_post"))
                ));
    }


    // COMMENTS
    // Adding comment
    public void addComment(Integer post_id, Integer author_id, String text, String date){
        jdbcTemplate.update("INSERT INTO comment(post_id,author_id,text,date_comment) VALUES (?,?,?,?)",
                new Object[]{post_id,author_id,text,date});
    }

    // Editing comment from id of the comment
    public void editComment(Integer id, String text, String date){
        jdbcTemplate.update("UPDATE comment SET text=?, date_comment=? WHERE id_comment=?",
                new Object[]{text,date,id});
    }
}
