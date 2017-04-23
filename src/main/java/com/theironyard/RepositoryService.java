package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JamesHartanto on 4/20/17.
 */
@Component
public class RepositoryService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    BlogRepository blogRepository;

    // List of usernames
    public ArrayList<String> usernameList(List<Person> list){
        ArrayList<String> userList = new ArrayList<>();
        for (int x = 0; x < list.size(); x = x + 1){
            userList.add(list.get(x).getUsername());
        }
        return userList;
    }

    // List of people based on blog id
    public ArrayList<BlogPerson> peoplePostList(List<Blog> blogList){
        ArrayList<BlogPerson> blogPerson = new ArrayList<>();
        for (int x = 0; x < blogList.size(); x = x + 1){
            blogPerson.add(new BlogPerson(blogList.get(x).getId(),
                    blogList.get(x).getPerson_id(),
                    blogList.get(x).getTitle(),
                    blogList.get(x).getPost(),
                    blogList.get(x).getDate(),
                    personRepository.selectPerson(blogList.get(x).getPerson_id()).getUsername()));
        }
        return blogPerson;
    }

    // A post with the username
    public BlogPerson usernameOfPost(Integer postId){
        Blog blog = blogRepository.viewABlog(postId);
        String username = "";

        // finding the username
        for (int x = 0; x < personRepository.listPeople().size(); x = x + 1){
            Person person = personRepository.listPeople().get(x);
            if (person.getId() == blog.getPerson_id()){
                username = person.getUsername();
            }
        }

        BlogPerson postWithUserName = new BlogPerson(blog.getId(),blog.getPerson_id(),blog.getTitle(),blog.getPost(),blog.getDate(),username);
        return postWithUserName;
    }

    // Checks if username is taken
    public boolean uniqueUsername(String username, ArrayList<String> list){
        if(list.contains(username)){
            return false;
        } else{
            return true;
        }
    }

    // Checks if blog belongs to user (permission to edit/delete)
    public boolean blogOwner(Blog blog, Person person){
        if (blog.getPerson_id() == person.getId()) {
            return true;
        }
            return false;
    }

}