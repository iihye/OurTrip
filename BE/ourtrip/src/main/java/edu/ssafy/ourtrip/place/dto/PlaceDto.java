package edu.ssafy.ourtrip.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceDto {
	private int placeNo;
	private int placeId;
	private String placeName;
	private String placeUrl;
	private String placeAddressName;
	private String placeRoadAddressName;
	private String placePhone;
	private String placeX;
	private String placeY;
	private int listNo;
}
