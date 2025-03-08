package com.wisehero.springbootpinpoint.loggingtest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/logging")
@Slf4j
public class LoggingController {

	@GetMapping("/debug")
	public void debug() {
		log.debug("=============================Debug=============================");
	}

	@GetMapping("/info")
	public void info() {
		log.info("=============================Info=============================");
	}

	@GetMapping("/warn")
	public void warn() {
		log.warn("=============================Warn=============================");
	}

	@GetMapping("/error")
	public void error() {
		log.error("=============================Error=============================",
			new RuntimeException("Logging Error"));
	}
}