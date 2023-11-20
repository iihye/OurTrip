package edu.ssafy.ourtrip.share.service;

import java.sql.SQLException;
import java.util.List;

import edu.ssafy.ourtrip.share.dto.ShareDto;
import edu.ssafy.ourtrip.share.dto.ShareDtoRes;

public interface ShareService {
	void add(ShareDto shareDto) throws SQLException;
	List<ShareDtoRes> find(ShareDto shareDto) throws SQLException;
	List<String> findOur(ShareDto shareDto) throws SQLException;
}
