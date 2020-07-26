package pl.chessWebApp.security.dao;

import pl.chessWebApp.security.entity.Role;

public interface RoleDao {
	Role findRoleByName(String theRoleName);
}
