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

    // List of usernames
    public ArrayList<String> usernameList(List<Person> list){
        ArrayList<String> userList = new ArrayList<>();
        for (int x = 0; x < list.size(); x = x + 1){
            userList.add(list.get(x).getUsername());
        }
        return userList;
    }

    // List of people based on blog id
    public ArrayList<String> usernameFromBlogId(List<Blog> blogList){
        ArrayList<String> usernameList = new ArrayList<>();
        for (int x = 0; x < blogList.size(); x = x + 1){
            usernameList.add(personRepository.selectPerson(blogList.get(x).getPerson_id()).getUsername());
        }
        return usernameList;
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