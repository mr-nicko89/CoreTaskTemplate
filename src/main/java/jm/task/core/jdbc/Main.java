package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;

public class Main {


    private static final String INSERT_NEW = "INSERT INTO users VALUES(?,?,?,?)";


    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        final UserService userService = new UserServiceImpl();

        try {
            userService.createUsersTable();

            userService.saveUser("Stive1", "Libovski", (byte) 43);

            for(int i = 2; i<100;i++) {
                userService.saveUser("Stive"+i, "Libovski", (byte) 44);
            }


//                userService.removeUserById(5);
//            List<User> listUser = userService.getAllUsers();
//            System.out.println(listUser);
//            userService.cleanUsersTable();
//            System.out.println("after clear table " + listUser);
//            System.out.println("Вывод все пользователей в консоль \n "+ userService.getAllUsers().toString());

//            userService.cleanUsersTable();
//            userService.dropUsersTable();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
