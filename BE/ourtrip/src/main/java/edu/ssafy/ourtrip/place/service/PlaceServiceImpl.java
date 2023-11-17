package edu.ssafy.ourtrip.place.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import edu.ssafy.ourtrip.place.dao.PlaceMapper;
import edu.ssafy.ourtrip.place.dto.PlaceDto;

@Service
public class PlaceServiceImpl implements PlaceService{
	private PlaceMapper placeMapper;
	
	@Autowired
	public PlaceServiceImpl(PlaceMapper placeMapper) {
		super();
		this.placeMapper = placeMapper;
	}


	public void registerPlace(PlaceDto placeDto) throws SQLException {
		placeMapper.registerPlace(placeDto);
	}

}
