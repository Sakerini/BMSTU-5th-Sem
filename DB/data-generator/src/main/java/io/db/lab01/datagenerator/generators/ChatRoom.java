package io.db.lab01.datagenerator.generators;

import com.github.javafaker.Faker;

import java.util.Random;

public class ChatRoom {
    private static int _id = 0;

    private int id;
    private String roomName;
    private int capacity;
    private String password;

    public ChatRoom() {
        _id++;
        this.id = _id;
        this.roomName = Faker.instance().app().name() + "Chat";
        Random rnd = new Random();
        this.capacity = rnd.nextInt((300 - 1) + 1) + 1;
        this.password = Faker.instance().internet().password();
    }

    public ChatRoom(String roomName, int capacity) {
        _id++;
        this.id = _id;
        this.roomName = roomName;
        this.capacity = capacity;
    }

    public static int get_id() {
        return _id;
    }

    public static void set_id(int _id) {
        ChatRoom._id = _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", capacity=" + capacity +
                ", password='" + password + '\'' +
                '}';
    }
}
