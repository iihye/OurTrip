package edu.ssafy.ourtrip.place.service;

import java.sql.SQLException;
import java.util.List;

import edu.ssafy.ourtrip.place.dto.PlaceDto;

public interface PlaceService {
	void registerPlace(List<PlaceDto> places) throws SQLException;
	void deletePlacesByListNo(int listNo) throws SQLException;
}
