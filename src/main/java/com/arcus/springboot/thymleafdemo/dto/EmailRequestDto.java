package com.arcus.springboot.thymleafdemo.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDto {

	private String from;
	private String to;
	private String subject;
	private String name;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
}