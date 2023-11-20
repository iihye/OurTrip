package edu.ssafy.ourtrip.share.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ssafy.ourtrip.share.dto.ShareDto;
import edu.ssafy.ourtrip.share.dto.ShareDtoRes;
import edu.ssafy.ourtrip.share.service.ShareService;
import edu.ssafy.ourtrip.user.dto.UserCheckDto;
import edu.ssafy.ourtrip.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/share")
@Slf4j
public class ShareController {
	private ShareService shareService;
	
	public ShareController(ShareService shareService) {
		this.shareService = shareService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<Map<String, Object>> add(@RequestBody ShareDto shareDto){
//		System.out.println(shareDto.toString());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;	
		try {
			shareService.add(shareDto);
			resultMap.put("message", "수정 성공");
			status = HttpStatus.OK;
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	} 
	
	@PostMapping("/find")
	public ResponseEntity<Map<String, Object>> find(@RequestBody ShareDto shareDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<ShareDtoRes> dto= shareService.find(shareDto);
			System.out.println(dto.toString());
			resultMap.put("list", dto);
			status = HttpStatus.OK;
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	} 
}
