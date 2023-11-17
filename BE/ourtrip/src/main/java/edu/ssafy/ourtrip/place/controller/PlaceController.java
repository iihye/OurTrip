package edu.ssafy.ourtrip.place.controller;

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
	
	@PostMapping
	public ResponseEntity<?> registerPlace(@RequestBody PlaceDto placeDto){
		return null;
		
	}
}
