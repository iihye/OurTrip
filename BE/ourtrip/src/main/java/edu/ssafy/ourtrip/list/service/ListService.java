package edu.ssafy.ourtrip.list.service;

import java.sql.SQLException;
import java.util.List;

import edu.ssafy.ourtrip.list.dto.ListDto;

public interface ListService {
	List<ListDto> myList(String userId) throws SQLException;
	List<ListDto> shareList(String userId) throws SQLException;
	List<ListDto> openList() throws SQLException;
}
