package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();

        userDaoJDBC.saveUser("samat", "kur", (byte) 22);
        userDaoJDBC.saveUser("Oskar", "miladze", (byte) 20);
        userDaoJDBC.saveUser("Uli", "Chaba", (byte) 23);
        userDaoJDBC.saveUser("Jokish", "kubanov", (byte) 22);

        for (User user : userDaoJDBC.getAllUsers()) {
            System.out.println(user);
        }

        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}
