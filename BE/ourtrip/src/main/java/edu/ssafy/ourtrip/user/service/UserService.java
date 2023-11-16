package edu.ssafy.ourtrip.user.service;

import java.sql.SQLException;
import java.util.Map;

import edu.ssafy.ourtrip.user.dto.UserDto;

public interface UserService {
	UserDto login(UserDto userDto) throws SQLException;
	UserDto userInfo(String userId) throws SQLException;
	void saveToken(String userId, String token) throws SQLException;
	Object getToken(String userId) throws SQLException;
	void deleteToken(String userId) throws SQLException;
}
