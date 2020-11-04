package com.bridgelabz.exception;

public class DBException extends Exception {

	public enum ExceptionType{
		CONNECTION_FAIL, SQL_ERROR, UPDATE_ERROR, RETRIEVE_ERROR, STATEMENT_FAILURE, INVALID_CONTACT_DATA
	}
	ExceptionType type;

	public DBException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}
}
