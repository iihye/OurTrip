package edu.ssafy.ourtrip.list.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.ssafy.ourtrip.list.dao.ListMapper;
import edu.ssafy.ourtrip.list.dto.ListDto;
import edu.ssafy.ourtrip.place.dto.PlaceDto;

@Service
public class ListServiceImpl implements ListService {
	
	private ListMapper listMapper;
	
	public ListServiceImpl(ListMapper listMapper) {
		this.listMapper = listMapper;
	}

	@Override
	public List<ListDto> myList(String userId) throws SQLException {
		return listMapper.myList(userId);
	}

	@Override
	public List<ListDto> shareList(String userId) throws SQLException {
		return listMapper.shareList(userId);
	}

	@Override
	public List<ListDto> openList() throws SQLException {
		return listMapper.openList();
	}

	@Override
	public List<PlaceDto> placesByListNo(int listNo) throws SQLException {
		return listMapper.placesByListNo(listNo);
	}

	@Override
	public int registerList(ListDto listDto) throws SQLException {
		int cnt = listMapper.registerList(listDto);
		return listDto.getListNo();
	}

	@Override
	public void deleteList(int listNo) throws SQLException {
		listMapper.deleteList(listNo);
	}

	@Override
	public void modifyList(ListDto listDto) throws SQLException {
		listMapper.modifyList(listDto);
	}

	@Override
	public ListDto detailByListNo(int listNo) throws SQLException {
		return listMapper.detailByListNo(listNo);
	}
}
