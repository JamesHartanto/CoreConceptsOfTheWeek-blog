package com.theironyard;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JamesHartanto on 4/20/17.
 */
@Component
public class RepositoryService {

    // List of usernames
    public ArrayList<String> usernameList(List<Person> list){
        ArrayList<String> userList = new ArrayList<>();
        for (int x = 0; x < list.size(); x = x + 1){
            userList.add(list.get(x).getUsername());
        }
        return userList;
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