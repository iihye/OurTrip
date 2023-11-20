package edu.ssafy.ourtrip.share.service;

import java.sql.SQLException;

import edu.ssafy.ourtrip.share.dto.ShareDto;

public interface ShareService {
	void add(ShareDto shareDto) throws SQLException;
	int find(ShareDto shareDto) throws SQLException;
}
