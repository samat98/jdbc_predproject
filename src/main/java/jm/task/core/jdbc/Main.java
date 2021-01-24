package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
        String s = "samat";

        userDaoJDBC.saveUser("samat", "kur", (byte) 22);
        userDaoJDBC.saveUser("Oskar", "miladze", (byte) 20);
        userDaoJDBC.saveUser("Uli", "Chaba", (byte) 23);
        userDaoJDBC.saveUser("Jokish", "kubanov", (byte) 22);

        userDaoJDBC.getAllUsers().forEach((user -> System.out.println(user)));

        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}
