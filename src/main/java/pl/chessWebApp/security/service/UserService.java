package pl.chessWebApp.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.chessWebApp.security.entity.User;
import pl.chessWebApp.security.user.CrmUser;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    User findByUserEmail(String userEmail);

    void save(CrmUser crmUser);
}
