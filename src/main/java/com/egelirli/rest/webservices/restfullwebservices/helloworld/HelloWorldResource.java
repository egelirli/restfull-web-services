package com.egelirli.rest.webservices.restfullwebservices.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldResource {

	private MessageSource messageSource;
	
	public HelloWorldResource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	//Path Variable or Path Params
	// /user/Ranga/todos/1
	
	@GetMapping("/hello-world-path-param/{name}")
	public HelloWorldBean helloWorldPathParam(@PathVariable String name) {
		return new HelloWorldBean("Hello World, " + name);
	}
	
	@GetMapping("/hello-world-path-param/{name}/message/{message}")
	public HelloWorldBean helloWorldMultiplePathParam
					(@PathVariable String name,
							@PathVariable String message) {
		return new HelloWorldBean("Hello World " + name + "," + message);
	}
	
	
	@GetMapping("/hello-world-intern")
	public String helloWorlInternationalized() {
		
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(
				"good.morning.message", null, "Default Message", locale);
	}

}
