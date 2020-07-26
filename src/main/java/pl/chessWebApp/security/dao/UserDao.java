package pl.chessWebApp.security.dao;

import pl.chessWebApp.security.entity.User;

public interface UserDao {

    User findByUserName(String userName);

    User findByUserEmail(String userName);

    void save(User user);
}
