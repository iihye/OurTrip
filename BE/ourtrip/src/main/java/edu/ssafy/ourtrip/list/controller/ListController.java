package edu.ssafy.ourtrip.list.controller;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<?> detailByListNo(@PathVariable("listNo") int listNo){
		try {
			ListDto listDto = listService.detailByListNo(listNo);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(listDto);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/places/{listNo}")
	public ResponseEntity<?> detail(@PathVariable("listNo") int listNo){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<PlaceDto> places = listService.placesByListNo(listNo);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(places);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
	
	@DeleteMapping("/delete/{listNo}")
	public ResponseEntity<String> deleteList(@PathVariable("listNo") int listNo) throws Exception {
		listService.deleteList(listNo);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/modify")
	public ResponseEntity<String> modifyList(@RequestBody ListDto listDto) throws Exception{
		listService.modifyList(listDto);
		return ResponseEntity.ok().build();
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
