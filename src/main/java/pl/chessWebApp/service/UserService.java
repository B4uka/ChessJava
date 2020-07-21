package pl.chessWebApp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.chessWebApp.entity.User;
import pl.chessWebApp.user.CrmUser;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    User findByUserEmail(String userEmail);

    void save(CrmUser crmUser);
}
