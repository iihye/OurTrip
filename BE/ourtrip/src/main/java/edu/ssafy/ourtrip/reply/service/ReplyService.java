package edu.ssafy.ourtrip.reply.service;

import java.sql.SQLException;
import java.util.List;

import edu.ssafy.ourtrip.reply.dto.LikeDto;
import edu.ssafy.ourtrip.reply.dto.ReplyDto;

public interface ReplyService {
	void regist(ReplyDto replyDto) throws SQLException;
	void delete(int replyNo) throws SQLException;
	int addLike(int replyNo) throws SQLException;
	int subLike(int replyNo) throws SQLException;
	int getLike(int replyNo) throws SQLException;
	List<ReplyDto> getReply(int listNo) throws SQLException;
	
	int addReplyLike(LikeDto likeDto) throws SQLException;
	int subReplyLike(LikeDto likeDto) throws SQLException;
	int getReplyLike(LikeDto likeDto) throws SQLException;
}
