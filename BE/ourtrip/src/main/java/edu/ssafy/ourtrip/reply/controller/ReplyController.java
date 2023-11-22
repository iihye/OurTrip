package edu.ssafy.ourtrip.reply.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ssafy.ourtrip.reply.dto.LikeDto;
import edu.ssafy.ourtrip.reply.dto.ReplyDto;
import edu.ssafy.ourtrip.reply.dto.ReplyGetDto;
import edu.ssafy.ourtrip.reply.dto.ReplyResDto;
import edu.ssafy.ourtrip.reply.service.ReplyService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reply")
@Slf4j
public class ReplyController {
	private ReplyService replyService;
	
	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}
	
	@PostMapping("/regist")
	public ResponseEntity<Map<String, Object>> join(@RequestBody ReplyDto replyDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			replyService.regist(replyDto);
			resultMap.put("message", "등록 성공");
			status = HttpStatus.CREATED;
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Map<String, Object>> delete(@RequestBody LikeDto likeDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			int cnt = replyService.delete(likeDto);
			
			System.out.print(cnt);
			resultMap.put("message", "삭제 성공");
			status = HttpStatus.CREATED;
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	} 
	
	@PostMapping("/addLike")
	public ResponseEntity<Map<String, Object>> addLike(@RequestBody LikeDto likeDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			int isAdd = replyService.addReplyLike(likeDto);
			if(isAdd > 0) {
				int cnt = replyService.addLike(likeDto.getReplyNo());
				resultMap.put("message", "좋아요 입력 성공");
				status = HttpStatus.OK;
			} else {
				resultMap.put("message", "좋아요 입력 실패");
				status = HttpStatus.FORBIDDEN;
			}
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/subLike")
	public ResponseEntity<Map<String, Object>> subLike(@RequestBody LikeDto likeDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			int isSub = replyService.subReplyLike(likeDto);
			if(isSub > 0) {
				int cnt = replyService.subLike(likeDto.getReplyNo());
				resultMap.put("message", "좋아요 취소 성공");
				status = HttpStatus.OK;
			}else {
				resultMap.put("message", "좋아요 취소 실패");
				status = HttpStatus.FORBIDDEN;
			}
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/getLike/{replyNo}")
	public ResponseEntity<Map<String, Object>> getLike(@PathVariable("replyNo") int replyNo){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			int cnt = replyService.getLike(replyNo);
			resultMap.put("replyLike", cnt);
			status = HttpStatus.CREATED;
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/getReply")
	public ResponseEntity<Map<String, Object>> getReply(@RequestBody ReplyGetDto replyGetDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<ReplyResDto> list= replyService.getReply(replyGetDto);
			resultMap.put("list", list);
			status = HttpStatus.CREATED;
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
