package edu.ssafy.ourtrip.user.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.ssafy.ourtrip.user.dao.UserMapper;
import edu.ssafy.ourtrip.user.dto.UserDto;

@Service
public class UserServiceImpl implements UserService{
	
	private UserMapper userMapper;
	
	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public UserDto login(UserDto userDto) throws SQLException {
		return userMapper.login(userDto);
	}

	@Override
	public UserDto userInfo(String userId) throws SQLException {
		return userMapper.userInfo(userId);
	}

	@Override
	public void saveToken(String userId, String token) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", token);
		userMapper.saveToken(map);
	}

	@Override
	public Object getToken(String userId) throws SQLException {
		return userMapper.getToken(userId);
	}

	@Override
	public void deleteToken(String userId) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		userMapper.deleteToken(map);
		
	}

}
