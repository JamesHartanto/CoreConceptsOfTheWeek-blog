package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JamesHartanto on 4/20/17.
 */
@Component
public class PersonRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    // List of people
    public List<Person> listPeople(){
        return jdbcTemplate.query("SELECT * FROM person",
                (resultSet,row) -> new Person(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password")));
    }

    // Select a person based on username
    public Person findPerson(String username){
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE lower(username) = ?",
                new Object[]{username},
                (resultSet, i) -> new Person(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                ));
    }

    // Create new user
    public void createPerson(Person person){
        jdbcTemplate.update("INSERT INTO person(username,password) VALUES(?,?)",
                new Object[]{person.getUsername(),person.getPassword()});
    }
}
