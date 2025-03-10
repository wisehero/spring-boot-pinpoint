package com.wisehero.springbootpinpoint.hello;

import com.wisehero.springbootpinpoint.hello.request.ExampleModelAttribute;
import com.wisehero.springbootpinpoint.hello.request.ExampleRequestBody;
import com.wisehero.springbootpinpoint.hello.request.HelloRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/example")
@RequiredArgsConstructor
@Slf4j
public class ExampleController {
	private final ExampleService exampleService;

	// 단순한 로그 출력
	@GetMapping
	public void example() {
		log.info("==================EXAMPLE==================");
	}

	@GetMapping("/hello")
	public void helloController() {
		log.info("helloController");
		exampleService.helloService();
	}

	@PostMapping("/hello")
	public void helloMessageController(@RequestBody HelloRequest request) {
		log.info("helloMessageController");
		exampleService.helloService();
	}

	// @RequestParam 요청
	@GetMapping("/request-param")
	public void requestParam(@RequestParam("name") String name, @RequestParam("description") String description) {
		exampleService.requestParamInService(name, description);
	}

	// ModelAttribute 요청
	@GetMapping("/model-attribute")
	public void modelAttribute(@Valid @ModelAttribute ExampleModelAttribute exampleModelAttribute) {
		exampleService.modelAttributeInService(exampleModelAttribute);
	}

	// @ModelAttribute 없이도 사용할 수 있다.
	@GetMapping("/without-model-attribute")
	public void withoutModelAttribute(@Valid ExampleModelAttribute exampleModelAttribute) {
		exampleService.modelAttributeInService(exampleModelAttribute);
	}

	// PathVariable 요청
	@GetMapping("/path-variable/{id}")
	public void pathVariable(@PathVariable("id") Long id) {
		exampleService.pathVariableInService(id);
	}

	// RequestBody 요청
	@PostMapping("/request-body")
	public void requestBody(@Valid @RequestBody ExampleRequestBody exampleRequestBody) {
		exampleService.requestBodyInService(exampleRequestBody);
	}

	// Put 요청
	@PutMapping("/request-body/with-path-variable/{id}")
	public void requestBodyWithPathVariable(@PathVariable Long id,
		@RequestBody ExampleRequestBody exampleRequestBody) {
		exampleService.requestBodyWithPathVariableInService(id, exampleRequestBody);
	}

	@GetMapping("/exception")
	public void exception() {

	}
}
