package edu.ssafy.ourtrip.reply.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.ssafy.ourtrip.reply.dao.ReplyMapper;
import edu.ssafy.ourtrip.reply.dto.LikeDto;
import edu.ssafy.ourtrip.reply.dto.ReplyDto;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	private ReplyMapper replyMapper;
	
	public ReplyServiceImpl(ReplyMapper replyMapper) {
		this.replyMapper = replyMapper;
	}

	@Override
	public void regist(ReplyDto replyDto) throws SQLException {
		replyMapper.regist(replyDto);
		
	}

	@Override
	public void delete(int replyNo) throws SQLException {
		replyMapper.delete(replyNo);
		
	}

	@Override
	public int addLike(int replyNo) throws SQLException {
		return replyMapper.addLike(replyNo);
	}

	@Override
	public int subLike(int replyNo) throws SQLException {
		return replyMapper.subLike(replyNo);
	}

	@Override
	public int getLike(int replyNo) throws SQLException {
		return replyMapper.getLike(replyNo);
	}

	@Override
	public List<ReplyDto> getReply(int listNo) throws SQLException {
		return replyMapper.getReply(listNo);
	}
	
	@Override
	public int addReplyLike(LikeDto likeDto) throws SQLException {
		return replyMapper.addReplyLike(likeDto);
	}

	@Override
	public int subReplyLike(LikeDto likeDto) throws SQLException {
		return replyMapper.subReplyLike(likeDto);
	}

	@Override
	public int getReplyLike(LikeDto likeDto) throws SQLException {
		return replyMapper.getReplyLike(likeDto);
	}

}
