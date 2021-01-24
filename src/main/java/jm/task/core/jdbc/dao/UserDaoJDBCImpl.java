package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Util util = new Util();
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet res;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE Users (" +
                "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(30) NOT NULL," +
                "lastName VARCHAR(30) NOT NULL," +
                "age INT(3) NOT NULL" +
                ") ENGINE=InnoDB";

        try {
            prepareConnection();
            stmt = conn.createStatement();
            System.out.println(conn.getMetaData().supportsSavepoints());
            stmt.executeUpdate(sql);
            conn.commit();
        } catch (SQLException e) {
            fillCatch(e);
        } finally {
            closeObj(stmt);
            closeObj(conn);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP table Users";

        try {
            prepareConnection();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate(sql);
            conn.commit();
        } catch (SQLException e) {
            fillCatch(e);
        } finally {
            closeObj(stmt);
            closeObj(conn);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users (name, lastName, age)" +
                "VALUES (?, ?, ?)";
        try {
            prepareConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            fillCatch(e);
        } finally {
            closeObj(pstmt);
            closeObj(conn);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE id=?";


        try {
            prepareConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            fillCatch(e);
        } finally {
            closeObj(pstmt);
            closeObj(conn);
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();

        try {
            prepareConnection();
            stmt = conn.createStatement();
            res = stmt.executeQuery(sql);
            while (res.next()) {
                User user = new User();
                user.setId(res.getLong("id"));
                user.setName(res.getString("name"));
                user.setLastName(res.getString("lastname"));
                user.setAge(res.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            fillCatch(e);
        } finally {
            closeObj(res);
            closeObj(pstmt);
            closeObj(conn);
        }

        return users;
    }

    public void cleanUsersTable() {
        String sql = "truncate Users";


        try {
            prepareConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            fillCatch(e);
        } finally {
            closeObj(pstmt);
            closeObj(conn);
        }
    }

    private void prepareConnection() throws SQLException{
        conn = util.getConnection();
        conn.setAutoCommit(false);
    }

    private void closeObj(AutoCloseable obj) {
        if (obj != null) {
            try {
                conn.setAutoCommit(true);
                obj.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void fillCatch(Exception e) {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
    }
}
