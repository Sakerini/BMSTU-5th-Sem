package io.db.lab01.datagenerator.generators;

import com.github.javafaker.Faker;

public class Message {

    private static int _id = 0;
    private int id;
    private int roomId;
    private int userId;
    private String message;
    private long timeStamp;

    public Message() {
        _id++;
        this.id = _id;
        this.message = Faker.instance().rickAndMorty().quote();
        this.timeStamp = new java.util.Date().getTime();
    }

    public Message(int roomId, int userId) {
        _id++;
        this.id = _id;
        this.roomId = roomId;
        this.userId = userId;
        this.message = Faker.instance().rickAndMorty().quote();
        this.timeStamp = new java.util.Date().getTime();
    }

    public Message(int roomId, int userId, String message, long timestamp) {
        _id++;
        this.id = _id;
        this.roomId = roomId;
        this.userId = userId;
        this.message = message;
        this.timeStamp = timestamp;
    }

    public static int get_id() {
        return _id;
    }

    public static void set_id(int _id) {
        Message._id = _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}

