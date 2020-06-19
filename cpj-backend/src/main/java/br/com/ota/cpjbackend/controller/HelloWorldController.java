package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessageProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class HelloWorldController {
	private static Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);
	private final MessageProperty messageProperty;

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
