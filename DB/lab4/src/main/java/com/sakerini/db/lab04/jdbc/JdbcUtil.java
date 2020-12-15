package com.sakerini.db.lab04.jdbc;

import java.sql.*;

public class JdbcUtil {

    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static String username = "C##Sakerini";
    private static String password = "991221";

    public static float getAvgAge() {
        String sql = "select avg(age) as avg_year from users";
        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                Statement ps = conn.createStatement();
                ResultSet rs = ps.executeQuery(sql);
                System.out.println("Statement executed!");
                while (rs.next()) {
                    System.out.println("Result: " + rs.getFloat("avg_year"));
                    return rs.getFloat("avg_year");
                }
                ps.close();
                rs.close();
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0f;
    }
    // Табличную
    public static Object xzfunc() {
        // id, room_name, capacity, password
        String sql = "{? = call first10(10)}";
        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                CallableStatement cs = conn.prepareCall(sql);
                cs.registerOutParameter(1, Types.ARRAY, "C##SAKERINI.T_TABLE");
                cs.execute();
                Object rs = cs.getArray(1).getArray();
                System.out.println(rs);
                cs.close();
                return rs;
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertRoom(String id, String roomName, String capacity, String password) {
        // id, room_name, capacity, password
        String sql = "call  INSERT_ROOM(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, id);
                ps.setString(2, roomName);
                ps.setString(3, capacity);
                ps.setString(4, password);

                ps.executeUpdate();
                ps.close();
                System.out.println("Statement executed!");

            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trigerDelete() {
        String sql = "delete from TRIGGERTEST where id = 3";
        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                Statement ps = conn.createStatement();
                ResultSet rs = ps.executeQuery(sql);
                System.out.println("Statement executed!");
                ps.close();
                rs.close();
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createAddressType() {
        String sql = "CREATE TYPE ADDRESS " +
                "(NUM INTEGER, STREET VARCHAR(40), " +
                "CITY VARCHAR(40), STATE CHAR(2), ZIP CHAR(5))";

        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                Statement ps = conn.createStatement();
                ResultSet rs = ps.executeQuery(sql);
                System.out.println("Statement executed!");
                ps.close();
                rs.close();
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
