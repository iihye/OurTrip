package edu.ssafy.ourtrip.place.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


	@Override
	public void registerPlace(PlaceDto placeDto) {
		placeMapper.registerPlace(placeDto);
	}

}
