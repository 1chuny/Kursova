package com.example.mbd.impl.inmemory;

import com.example.mbd.dao.UserDao;
import com.example.mbd.model.User;

class InMemoryUserDao extends InMemoryAbstractDao<User> implements UserDao {

    InMemoryUserDao(InMemoryDataBase database) {
        super(database.users, User::getUserId, User::setUserId, database);
    }

    @Override
    public User getByLogin(String login) {
        return database.users.values()
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

}