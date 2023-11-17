package edu.ssafy.ourtrip.place.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ssafy.ourtrip.place.dto.PlaceDto;
import edu.ssafy.ourtrip.place.service.PlaceService;

@RestController
@RequestMapping("/place")
public class PlaceController {
	private PlaceService placeService;

	public PlaceController(PlaceService placeService) {
		super();
		this.placeService = placeService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerPlace(@RequestBody PlaceDto placeDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			placeService.registerPlace(placeDto);
			status = HttpStatus.OK;
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<Map<String, Object>>(resultMap, status);
		}
	}
}
