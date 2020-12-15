package com.sakerini.db.lab06;

import com.sakerini.db.lab06.jdbc.JdbcUtil;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        printMenu();
        int input = getInput();
        processInput(input);
    }

    private static void printMenu() {
        System.out.println("============MENU============");
        System.out.println("1) Get room capacity");
        System.out.println("2) Get user's rooms");
        System.out.println("3) Get username count");
        System.out.println("4) Get meta data for table");
        System.out.println("5) Get user age by ID");
        System.out.println("6) Get user names from room");
        System.out.println("7) Insert new room");
        System.out.println("8) Get current system database user");
        System.out.println("9) Create moderators table");
        System.out.println("10) Add moderator");
        System.out.println("0) Exit");
    }

    public static int getInput() {
        System.out.print("Enter number: ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        while ((choice < 0 || choice >= 11)) {
            System.out.print("Wrong Input enter number again: ");
            choice = sc.nextInt();
        }
        return choice;
    }

    private static void processInput(int input) {
        Scanner sc = new Scanner(System.in);
        Integer id;

        switch (input){
            case 0:
                System.exit(0);
            case 1:
                System.out.print("Enter room id: ");
                id = sc.nextInt();
                System.out.println("Connecting to database...");
                System.out.println("CAPACITY: " + JdbcUtil.getRoomCapacity(id));
                break;
            case 2:
                System.out.print("Enter id: ");
                id = sc.nextInt();
                System.out.println("Connecting to database...");
                System.out.println("-NAMES-");
                List<String> names = JdbcUtil.getRoomsByUserId(id);
                printStringArray(names);
                break;
            case 3:
                Map<String, Integer> map = JdbcUtil.getUsernameCount();
                for (String el: map.keySet()) {
                    System.out.println("Name: " + el + " Count: " + map.get(el));
                }
                break;
            case 4:
                System.out.println("Enter table name: ");
                String tableName = sc.next();
                Map<String, String> metaMap = JdbcUtil.getMetaDataForTable(tableName);
                for (String key: metaMap.keySet()) {
                    System.out.println("ColumnName: " + key + " Data_type: " + metaMap.get(key));
                }
                break;
            case 5:
                System.out.print("Enter id: ");
                id = sc.nextInt();
                System.out.println("Connecting to database...");
                System.out.println("AGE: " + JdbcUtil.getAgeById(id));
                break;
            case 6:
                System.out.print("Enter room id: ");
                id = sc.nextInt();
                System.out.println("Connecting to database...");
                System.out.println("-NAMES-");
                List<String> roomNames = JdbcUtil.getUsersFromRoom(id);
                printStringArray(roomNames);
                break;
            case 7:
                System.out.print("Enter room id: ");
                id = sc.nextInt();
                System.out.print("Enter room name:");
                String roomName = sc.next();
                System.out.print("Enter room capacity");
                Integer roomCap = sc.nextInt();
                System.out.print("Enter room password");
                String roomPassword = sc.next();
                JdbcUtil.insertRoom(id.toString(), roomName, roomCap.toString(), roomPassword);
                System.out.println("Successful inserting...");
                break;
            case 8:
                System.out.println("DB USER: " + JdbcUtil.getSystemDBUser());
                break;
            case 9:
                JdbcUtil.createModeratorTable();
                break;
            case 10:
                System.out.print("Enter moderator id: ");
                id = sc.nextInt();
                System.out.print("Enter user id: ");
                Integer userId = sc.nextInt();
                System.out.print("Enter moderator level: ");
                Integer modLevel = sc.nextInt();
                JdbcUtil.insertModerator(id, userId, modLevel);
                break;
        }
        run();
    }

    private static void printStringArray(List<String> array) {
        for (String element: array) {
            System.out.println(element);
        }
    }
}
