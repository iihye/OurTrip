package edu.ssafy.ourtrip.list.service;

import java.sql.SQLException;
import java.util.List;

import edu.ssafy.ourtrip.list.dto.ListDto;
import edu.ssafy.ourtrip.place.dto.PlaceDto;

public interface ListService {
	List<PlaceDto> detail(String listNo) throws SQLException;
	List<ListDto> myList(String userId) throws SQLException;
	List<ListDto> shareList(String userId) throws SQLException;
	List<ListDto> openList() throws SQLException;
	void registerList(ListDto listDto) throws SQLException;
}
