package edu.ssafy.ourtrip.reply.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.ourtrip.reply.dto.LikeDto;
import edu.ssafy.ourtrip.reply.dto.ReplyDto;
import edu.ssafy.ourtrip.reply.dto.ReplyGetDto;
import edu.ssafy.ourtrip.reply.dto.ReplyResDto;

@Mapper
public interface ReplyMapper {
	void regist(ReplyDto replyDto) throws SQLException;
	void delete(LikeDto likeDto) throws SQLException;
	int addLike(int replyNo) throws SQLException;
	int subLike(int replyNo) throws SQLException;
	int getLike(int replyNo) throws SQLException;
	List<ReplyResDto> getReply(ReplyGetDto replyGetDto) throws SQLException;
	
	int addReplyLike(LikeDto likeDto) throws SQLException;
	int subReplyLike(LikeDto likeDto) throws SQLException;
}
