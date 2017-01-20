package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;

@RestController
public class LogController {

	private static final Logger logger = LoggerFactory.getLogger(LogController.class);

	@PostConstruct
	public void logSomething() {
		logger.debug("Sample Debug Message");
		logger.trace("Sample Trace Message");
	}

	@GetMapping("/info/{message}")
	public String logInfo(@PathVariable("message") String message) {
		logger.info("Info Message: {}", message);
		return String.format("Info Message: %s", message);
	}

	@GetMapping("/debug/{message}")
	public String logDebug(@PathVariable("message") String message) {
		logger.debug("Debug Message: {}", message);
		return String.format("Debug Message: %s", message);
	}

	@GetMapping("/trace/{message}")
	public String logTrace(@PathVariable("message") String message) {
		logger.trace("Trace Message: {}", message);
		return String.format("Trace Message: %s", message);
	}
}
