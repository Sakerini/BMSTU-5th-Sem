package io.db.lab01.datagenerator.generators;

import com.github.javafaker.Faker;

import java.util.Random;

public class User {
    private static int _id = 0;

    private int id;
    private String username;
    private String password;
    private String email;
    private char gender;
    private int age;


    public User() {
        _id++;
        this.id = _id;
        this.username = Faker.instance().name().firstName();
        this.password = Faker.instance().internet().password();
        this.email = Faker.instance().internet().emailAddress();
        this.gender = Faker.instance().dog().gender().charAt(0);
        Random rnd = new Random();
        this.age = rnd.nextInt((60 - 18) + 1) + 18;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password, String email, char gender, int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.age = age;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }
}
