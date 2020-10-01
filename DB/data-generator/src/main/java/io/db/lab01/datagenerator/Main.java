package io.db.lab01.datagenerator;

import com.github.javafaker.Faker;
import io.db.lab01.datagenerator.generators.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private final static int sizeOfEntries = 1000;
    private final static List<User> users = new ArrayList<>(sizeOfEntries);
    private final static List<ChatRoom> chatRooms = new ArrayList<>(sizeOfEntries);
    private final static List<Message> messages = new ArrayList<>(sizeOfEntries);
    private final static List<Participant> participants = new ArrayList<>(sizeOfEntries);

    public static void main(String[] args) throws Exception {

        Random rnd = new Random();
        // Generate Rooms And Users
        for (int i = 0; i < sizeOfEntries; i++) {
            User user = new User();
            users.add(user);
            ChatRoom room = new ChatRoom();
            chatRooms.add(room);
        }
        // Generate Participants

        for (int i = 0; i < chatRooms.size(); i++) {
            int usersInRoom = rnd.nextInt((30 - 2) + 1) + 2;
            for (int j = 0; j < usersInRoom; j++) {
                Participant participant = new Participant();
                participant.setRoomId(chatRooms.get(i).getId());
                int userRandomIndex = rnd.nextInt((users.size() - 1 - 0) + 0);
                participant.setUserId(users.get(userRandomIndex).getId());
                participants.add(participant);
            }
        }

        // Generate Messages

        for (int i = 0; i < sizeOfEntries; i++) {
            Message message = new Message();
            int roomRandIndex = rnd.nextInt((chatRooms.size() - 1 - 0) + 0);
            int userRandomIndex = rnd.nextInt((users.size() - 1 - 0) + 0);
            message.setUserId(users.get(userRandomIndex).getId());
            message.setRoomId(chatRooms.get(roomRandIndex).getId());
            messages.add(message);
        }

        String dir = "../csv/";
        new File(dir).mkdir();

        // Write everything
        writeChatRooms(dir + "chatrooms.csv");
        writeUsers(dir + "users.csv");
        writeMessages(dir + "messages.csv");
        writeParticipants(dir + "participants.csv");
    }

    private static void writeChatRooms(String destination) throws Exception {
        FileWriter fw = new FileWriter(destination);

        final String[] headers = {"id", "room_name", "capacity", "password"};

        try (CSVPrinter printer = new CSVPrinter(fw, CSVFormat.ORACLE.withHeader(headers))) {
            for (ChatRoom room : chatRooms) {
                printer.printRecord(
                        room.getId(),
                        room.getRoomName(),
                        room.getCapacity(),
                        room.getPassword()
                );
            }
        }
    }

    private static void writeUsers(String destination) throws Exception {
        FileWriter fw = new FileWriter(destination);

        final String[] headers = {"id", "username", "password", "email", "gender", "age"};

        try (CSVPrinter printer = new CSVPrinter(fw, CSVFormat.ORACLE.withHeader(headers))) {
            for (User user : users) {
                printer.printRecord(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getGender(),
                        user.getAge()
                );
            }
        }
    }

    private static void writeMessages(String destination) throws Exception {
        FileWriter fw = new FileWriter(destination);

        final String[] headers = {"id", "room_id", "user_id", "message", "timestamp"};

        try (CSVPrinter printer = new CSVPrinter(fw, CSVFormat.ORACLE.withHeader(headers))) {
            for (Message message : messages) {
                printer.printRecord(
                        message.getId(),
                        message.getRoomId(),
                        message.getUserId(),
                        message.getMessage(),
                        message.getTimeStamp()
                );
            }
        }
    }

    private static void writeParticipants(String destination) throws Exception {
        FileWriter fw = new FileWriter(destination);

        final String[] headers = {"id", "room_id", "user_id"};

        try (CSVPrinter printer = new CSVPrinter(fw, CSVFormat.ORACLE.withHeader(headers))) {
            for (Participant participant : participants) {
                printer.printRecord(
                        participant.getId(),
                        participant.getRoomId(),
                        participant.getUserId()
                );
            }
        }
    }
}