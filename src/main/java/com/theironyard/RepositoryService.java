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
    public ArrayList<BlogPerson> peoplePostList(List<Post> postList){
        ArrayList<BlogPerson> blogPerson = new ArrayList<>();
        for (int x = 0; x < postList.size(); x = x + 1){
            blogPerson.add(new BlogPerson(postList.get(x).getId(),
                    postList.get(x).getPerson_id(),
                    postList.get(x).getTitle(),
                    postList.get(x).getPost(),
                    postList.get(x).getDate(),
                    personRepository.selectPerson(postList.get(x).getPerson_id()).getUsername()));
        }
        return blogPerson;
    }

    // A post with the username
    public BlogPerson usernameOfPost(Integer postId){
        Post post = blogRepository.viewABlog(postId);
        String username = "";

        // finding the username
        for (int x = 0; x < personRepository.listPeople().size(); x = x + 1){
            Person person = personRepository.listPeople().get(x);
            if (person.getId() == post.getPerson_id()){
                username = person.getUsername();
            }
        }

        BlogPerson postWithUserName = new BlogPerson(post.getId(), post.getPerson_id(), post.getTitle(), post.getPost(), post.getDate(),username);
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

}