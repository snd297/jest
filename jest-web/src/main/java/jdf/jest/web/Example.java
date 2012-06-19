package jdf.jest.web;

import com.google.sitebricks.At;

@At("/")
public class Example {
	private String message = "Hello";

	public String getMessage() {
		return message;
	}
}