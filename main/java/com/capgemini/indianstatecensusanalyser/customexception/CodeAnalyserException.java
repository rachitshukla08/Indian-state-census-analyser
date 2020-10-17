package com.capgemini.indianstatecensusanalyser.customexception;

public class CodeAnalyserException extends Exception {
	public enum ExceptionType {
		INVALID_FILE_PATH, INVALID_CLASS_TYPE, INVALID_DELIMITER, INVALID_HEADER
	}
	public ExceptionType type;
	
	public CodeAnalyserException(String message,ExceptionType type) {
		super(message);
		this.type = type;
	}
}