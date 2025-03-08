package com.wisehero.springbootpinpoint.hello;

import com.wisehero.springbootpinpoint.hello.request.ExampleModelAttribute;
import com.wisehero.springbootpinpoint.hello.request.ExampleRequestBody;
import com.wisehero.springbootpinpoint.hello.request.HelloRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExampleService {

	private final ExampleRepository exampleRepository;

	public void helloService() {
		log.info("helloService");
	}

	public void helloService2(HelloRequest request) {
		log.info("helloService2");
		log.info("request: {}", request);
	}

	public void requestParamInService(String name, String description) {
		log.debug("name: {}, description: {}", name, description);
		log.info("name: {}, description: {}", name, description);
	}

	public void modelAttributeInService(ExampleModelAttribute exampleModelAttribute) {
		log.debug("exampleModelAttribute: {}", exampleModelAttribute);
		log.info("exampleModelAttribute: {}", exampleModelAttribute);
	}

	public void pathVariableInService(Long id) {
		log.debug("id: {}", id);
		log.info("id: {}", id);
	}

	public void pathVariableWithRequestParamInService(Long id, String name) {
		log.debug("id: {}, name: {}", id, name);
		log.info("id: {}, name: {}", id, name);
	}

	public void pathVariableWithModelAttributeInService(Long id, ExampleModelAttribute exampleModelAttribute) {
		log.debug("id: {}, exampleModelAttribute: {}", id, exampleModelAttribute);
		log.info("id: {}, exampleModelAttribute: {}", id, exampleModelAttribute);
	}

	public void requestBodyInService(ExampleRequestBody exampleRequestBody) {
		log.debug("exampleRequestBody: {}", exampleRequestBody);
		log.info("exampleRequestBody: {}", exampleRequestBody);

		ExampleEntity exampleEntity = ExampleEntity.of(exampleRequestBody);
		exampleRepository.save(exampleEntity);
	}

	public void requestBodyWithPathVariableInService(Long id, ExampleRequestBody exampleRequestBody) {
		log.debug("id: {}, exampleRequestBody: {}", id, exampleRequestBody);
		log.info("id: {}, exampleRequestBody: {}", id, exampleRequestBody);
		ExampleEntity entity = exampleRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Not found"));

		entity.updateEntity(exampleRequestBody);
		exampleRepository.save(entity);
	}
}
