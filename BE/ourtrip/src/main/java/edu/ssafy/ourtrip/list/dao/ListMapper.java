package edu.ssafy.ourtrip.list.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.ourtrip.list.dto.ListDto;
import edu.ssafy.ourtrip.place.dto.PlaceDto;

@Mapper
public interface ListMapper {
	List<PlaceDto> detail(String listNo) throws SQLException;
	List<ListDto> myList(String userId) throws SQLException;
	List<ListDto> shareList(String userId) throws SQLException;
	List<ListDto> openList() throws SQLException;
	int registerList(ListDto listDto) throws SQLException;
	void deleteList(int listNo);
}
