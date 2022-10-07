package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private String sql = null;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        if (tableExists()) {
            return;
        }

        sql = "CREATE TABLE users " +
                "(id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NULL, " +
                "lastname VARCHAR(45) NULL, " +
                "age INT(3) NULL, " +
                "PRIMARY KEY ( id )) ";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        if (!tableExists()) {
            return;
        }

        sql = "DROP TABLE users";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        if (!tableExists()) {
            return;
        }

        sql = String.format("INSERT INTO users (name, lastname, age) " +
                "VALUES ('%s', '%s', '%d')", name, lastName, age);

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        if (!tableExists()) {
            return;
        }

        sql = "DELETE FROM users WHERE id = " + id;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        sql = "SELECT * FROM users";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void cleanUsersTable() {
        sql = "TRUNCATE TABLE users";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Checking table
    public boolean tableExists() {
        boolean isExists = false;

        try (Connection connection = getConnection()) {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, "USERS", new String[]{"TABLE"});
            isExists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isExists;
    }
}
