package pl.chessWebApp.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.chessWebApp.security.dao.RoleDao;
import pl.chessWebApp.security.dao.UserDao;
import pl.chessWebApp.security.entity.Role;
import pl.chessWebApp.security.entity.User;
import pl.chessWebApp.security.user.CrmUser;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    public UserServiceImpl (UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public User findByUserName (String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    @Transactional
    public User findByUserEmail (String userEmail) {
        return userDao.findByUserEmail(userEmail);
    }

    @Override
    @Transactional
    public void save (CrmUser crmUser) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setUserName(crmUser.getUserName());
        user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
        user.setFirstName(crmUser.getFirstName());
        user.setLastName(crmUser.getLastName());
        user.setEmail(crmUser.getEmail());

        user.setRoles(Collections.singletonList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
        userDao.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername (String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities (Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
