package edu.ssafy.ourtrip.share.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.ourtrip.share.dto.ShareDto;
import edu.ssafy.ourtrip.share.dto.ShareDtoRes;

@Mapper
public interface ShareMapper {
	void add(ShareDto shareDto) throws SQLException;
	List<ShareDtoRes> find(ShareDto shareDto) throws SQLException;
	List<String> findOur(ShareDto shareDto) throws SQLException;
}
