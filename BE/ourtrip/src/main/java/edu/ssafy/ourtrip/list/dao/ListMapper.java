package edu.ssafy.ourtrip.list.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.ourtrip.list.dto.ListDto;

@Mapper
public interface ListMapper {
	List<ListDto> myList(String userId) throws SQLException;
	List<ListDto> shareList(String userId) throws SQLException;
	List<ListDto> openList() throws SQLException;
}
