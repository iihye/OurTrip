package edu.ssafy.ourtrip.list.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ssafy.ourtrip.list.dto.ListDto;
import edu.ssafy.ourtrip.list.service.ListService;
import edu.ssafy.ourtrip.place.dto.PlaceDto;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/list")
@Slf4j
public class ListController {
	private ListService listService;
	
	public ListController(ListService listService) {
		this.listService = listService;
	}
	
	@GetMapping("/detail/{listNo}")
	public ResponseEntity<Map<String, Object>> detail(@PathVariable("listNo") String listNo){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<PlaceDto> list = listService.detail(listNo);
			System.out.println(list.toString());
			status = HttpStatus.OK;
			resultMap.put("list", list);
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/my/{userId}")
	public ResponseEntity<Map<String, Object>> myList(@PathVariable("userId") String userId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<ListDto> list = listService.myList(userId);
			System.out.println(list.toString());
			status = HttpStatus.OK;
			resultMap.put("list", list);
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/share/{userId}")
	public ResponseEntity<Map<String, Object>> ourList(@PathVariable("userId") String userId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<ListDto> list = listService.shareList(userId);
			System.out.println(list.toString());
			status = HttpStatus.OK;
			System.out.println(list.isEmpty());
			resultMap.put("list", list);
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/open")
	public ResponseEntity<Map<String, Object>> openList(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<ListDto> list = listService.openList();
			System.out.println(list.toString());
			status = HttpStatus.OK;
			resultMap.put("list", list);
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerList(@RequestBody ListDto listDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			int listNo = listService.registerList(listDto);
			System.out.println(listNo);
			status = HttpStatus.CREATED;
			resultMap.put("listNo", listNo);
			return new ResponseEntity<Map<String, Object>>(resultMap, status);
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<Map<String, Object>>(resultMap, status);
		}
		
		
	}
}
