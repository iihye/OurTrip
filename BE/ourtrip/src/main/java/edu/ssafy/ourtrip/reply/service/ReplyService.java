package edu.ssafy.ourtrip.reply.service;

import java.sql.SQLException;
import java.util.List;

import edu.ssafy.ourtrip.reply.dto.LikeDto;
import edu.ssafy.ourtrip.reply.dto.ReplyDto;
import edu.ssafy.ourtrip.reply.dto.ReplyGetDto;
import edu.ssafy.ourtrip.reply.dto.ReplyResDto;

public interface ReplyService {
	void regist(ReplyDto replyDto) throws SQLException;
	int delete(LikeDto likeDto) throws SQLException;
	int addLike(int replyNo) throws SQLException;
	int subLike(int replyNo) throws SQLException;
	int getLike(int replyNo) throws SQLException;
	List<ReplyResDto> getReply(ReplyGetDto replyGetDto) throws SQLException;
	
	int addReplyLike(LikeDto likeDto) throws SQLException;
	int subReplyLike(LikeDto likeDto) throws SQLException;
}
