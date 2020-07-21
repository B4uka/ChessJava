package pl.chessWebApp.dao;

import pl.chessWebApp.entity.Role;

public interface RoleDao {
	Role findRoleByName(String theRoleName);
}
