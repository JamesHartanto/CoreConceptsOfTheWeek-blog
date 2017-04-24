package com.theironyard;

/**
 * Created by JamesHartanto on 4/24/17.
 */
public class PostComment {
    Integer id;
    String text;
    BlogPerson commentAuthor;

    public PostComment() {
    }

    public PostComment(Integer id, String text, BlogPerson commentAuthor) {
        this.id = id;
        this.text = text;
        this.commentAuthor = commentAuthor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BlogPerson getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(BlogPerson commentAuthor) {
        this.commentAuthor = commentAuthor;
    }
}
