package edu.ssafy.ourtrip.place.dao;

import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.ourtrip.place.dto.PlaceDto;

@Mapper
public interface PlaceMapper {
	void registerPlace(PlaceDto placeDto);
}
