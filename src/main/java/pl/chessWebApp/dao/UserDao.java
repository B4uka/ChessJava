package pl.chessWebApp.dao;

import pl.chessWebApp.entity.User;

public interface UserDao {

    User findByUserName(String userName);

    User findByUserEmail(String userName);

    void save(User user);
}
