package io.db.lab01.datagenerator.generators;

public class Participant {

    private static int _id = 0;
    private int id;
    private int roomId;
    private int userId;

    public Participant () {
        _id++;
        this.id = _id;
    }

    public Participant(int roomId, int userId) {
        _id++;
        this.id = _id;
        this.roomId = roomId;
        this.userId = userId;
    }

    public static int get_id() {
        return _id;
    }

    public static void set_id(int _id) {
        Participant._id = _id;
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

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", userId=" + userId +
                '}';
    }
}
