package edu.ssafy.ourtrip.list.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListDto {
	private int listNo;
	private String listName;
	private int listImg;
	private boolean listOpen;
	private String userId;
}
