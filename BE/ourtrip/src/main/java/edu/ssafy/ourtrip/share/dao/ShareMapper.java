package edu.ssafy.ourtrip.share.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.ourtrip.share.dto.ShareDto;

@Mapper
public interface ShareMapper {
	void add(ShareDto shareDto) throws SQLException;
	int find(ShareDto shareDto) throws SQLException;
}
