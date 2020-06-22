package br.com.ota.cpjbackend.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class HelloWorldController {
	private static Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);

	@GetMapping("/all")
	public @ResponseBody String sayHello() {
		return "Home";
	}

	@GetMapping("/user")
	public @ResponseBody String getUser() {
		LOG.info("GET called on /user resource");
		return "User Board!";
	}

}
