package com.example.mbd.services;

import com.example.mbd.dao.UserDao;
import com.example.mbd.model.User;
import java.util.function.UnaryOperator;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UnaryOperator<String> passwordHasher;

    public UserServiceImpl(UserDao userDao, UnaryOperator<String> passwordHasher) {
        this.userDao = userDao;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public User getByLogin(String login) {
        return userDao.getByLogin(login);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return user.getPasswordHash().equals(passwordHasher.apply(password));
    }

    @Override
    public User authenticate(String login, String password) {
        User user = getByLogin(login);
        if (user != null && checkPassword(user, password)) {
            return user;
        }
        return null;
    }
}
