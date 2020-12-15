package com.sakerini.db.lab06.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtil {

    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static String username = "C##Sakerini";
    private static String password = "991221";


    public static Map<String, String> getMetaDataForTable(String tableName) {
        String sql = "SELECT T.COLUMN_NAME, T.DATA_TYPE\n" +
                "        FROM ALL_TAB_COLUMNS T\n" +
                "        WHERE T.TABLE_NAME = 'USERS'";

        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                PreparedStatement ps = conn.prepareStatement(sql);
                try (ResultSet rs = ps.executeQuery()) {
                    Map<String, String> metaMap = new HashMap<>();
                    List<String> array = new ArrayList<>();
                    while (rs.next()) {
                        metaMap.put(rs.getString(1), rs.getString(2));
                    }
                    rs.close();
                    ps.close();
                    return metaMap;
                }
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


    public static Map<String, Integer> getUsernameCount() {
        String sql = "with username_count as (\n" +
                "select USERNAME, count(*) as user_count\n" +
                "    from USERS group by USERNAME )\n" +
                "select u.USERNAME as Username, uc.user_count from USERS u join username_count uc on u.USERNAME = uc.USERNAME";

        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                PreparedStatement ps = conn.prepareStatement(sql);
                try (ResultSet rs = ps.executeQuery()) {
                    Map<String, Integer> usernameMap = new HashMap<>();
                    List<String> array = new ArrayList<>();
                    while (rs.next()) {
                        usernameMap.put(rs.getString(1), rs.getInt(2));
                    }
                    rs.close();
                    ps.close();
                    return usernameMap;
                }
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

    public static int getRoomCapacity(int roomId) {
        String sql = "select CAPACITY from CHAT_ROOMS where id = ?";
        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, roomId);
                ResultSet rs = ps.executeQuery();
                System.out.println("Statement executed!");
                while (rs.next()) {
                    return rs.getInt("CAPACITY");
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
        return -1;
    }

    public static void insertModerator(int id, int userId, int level) {
        // id, room_name, capacity, password
        String sql = "insert into moderators values(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setInt(1, id);
                ps.setInt(2, userId);
                ps.setInt(3, level);

                ps.executeUpdate();
                ps.close();
                System.out.println("Moderator added!");

            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dropModeratorTable() {
        String drop = "drop table moderators";

        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                Statement ps = conn.createStatement();
                ResultSet rs = ps.executeQuery(drop);
                System.out.println("Table dropped");
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

    public static void createModeratorTable() {
        String sql = "create table moderators(\n" +
                "    id Integer,\n" +
                "    userid integer,\n" +
                "    mod_level integer\n" +
                ")";

        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                Statement ps = conn.createStatement();
                ResultSet rs = ps.executeQuery(sql);
                System.out.println("Table created!");
                ps.close();
                rs.close();
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("42000")) {
                dropModeratorTable();
            }
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getUsersFromRoom(int roomId) {
        String sql = "select * from table (getUsersFromRoom(?))";
        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,roomId);
                try (ResultSet rs = ps.executeQuery()) {
                    List<String> array = new ArrayList<>();
                    while (rs.next()) {
                        array.add(rs.getString(1));
                    }
                    rs.close();
                    ps.close();
                    return array;
                }
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

    public static String getSystemDBUser() {
        String sql = "SELECT USER\n" +
                "  FROM DUAL";

        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                Statement ps = conn.createStatement();
                ResultSet rs = ps.executeQuery(sql);
                while (rs.next()) {
                    String name = rs.getString(1);
                    ps.close();
                    rs.close();
                    return name;
                }
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

    // Табличную
    public static Object xzfunc() {
        // id, room_name, capacity, password
        String sql = "{? = call first10(10)}";
        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                CallableStatement cs = conn.prepareCall(sql);
                cs.registerOutParameter(1, Types.ARRAY, "varchar2");
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

    public static List<String> getRoomsByUserId(int id) {
        // id, room_name, capacity, password
        String sql = "select * from table (GET_ROOM_BY_USER_ID(?))";
        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,id);
                try (ResultSet rs = ps.executeQuery()) {
                    List<String> array = new ArrayList<>();
                    while (rs.next()) {
                        array.add(rs.getString(1));
                    }
                    rs.close();
                    ps.close();
                    return array;
                }
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

    public static int getAgeById(int id) {
        int res = -1;
        String sql = "{? = call get_age_by_id(?)}";
        try (Connection conn = DriverManager.getConnection(
                url, username, JdbcUtil.password)) {

            if (conn != null) {
                CallableStatement cs = conn.prepareCall(sql);
                cs.registerOutParameter(1, Types.INTEGER);
                cs.setInt(2, id);
                cs.executeUpdate();
                res = cs.getInt(1);
                cs.close();
                return res;
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
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
}
