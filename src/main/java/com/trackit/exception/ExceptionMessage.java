package com.trackit.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Builder
public class ExceptionMessage {

	private String path;
	private String date;
	private String className;
	private String message;

}
