package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Tommy", "Small", (byte) 25);
        userService.saveUser("Micky", "Big", (byte) 54);
        userService.saveUser("Ann", "Black", (byte) 11);
        userService.saveUser("Boris", "White", (byte) 39);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}