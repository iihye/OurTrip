package edu.ssafy.ourtrip.user.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.ourtrip.user.dto.UserDto;

@Mapper
public interface UserMapper {
	void update(UserDto userDto) throws SQLException;
	String find(UserDto userDto) throws SQLException;
	int check(UserDto userDto) throws SQLException;
	void join(UserDto userDto) throws SQLException;
	UserDto login(UserDto userDto) throws SQLException;
	UserDto userInfo(String userId) throws SQLException;
	void saveToken(Map<String, String> map) throws SQLException;
	Object getToken(String userId) throws SQLException;
	void deleteToken(Map<String, String> map) throws SQLException;
}
