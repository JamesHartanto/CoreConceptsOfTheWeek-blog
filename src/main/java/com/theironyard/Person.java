package com.theironyard;

/**
 * Created by JamesHartanto on 4/20/17.
 */
public class Person {
    private int id;
    private String username;
    private String password;

    public Person(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Person() {
    }

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
