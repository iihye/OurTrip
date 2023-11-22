package edu.ssafy.ourtrip.reply.dto;

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
public class ReplyResDto {
	private int replyNo;
	private String replyContent;
	private String replyDatetime;
	private int replyLike;
	private int listNo;
	private String userId;
	private boolean status;	
}
