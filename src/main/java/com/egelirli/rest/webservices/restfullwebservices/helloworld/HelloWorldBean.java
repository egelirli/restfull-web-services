package com.egelirli.rest.webservices.restfullwebservices.helloworld;

public class HelloWorldBean {
	
	public HelloWorldBean(String message) {
		super();
		this.message = message;
	}

	private String message;

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "HelloWorldBean [message=" + message + "]";
	}
}
