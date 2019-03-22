package com.trackit.exception;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionMessage {
	
	private String date;
	private String path;
	private String className;
	private String message;

}
