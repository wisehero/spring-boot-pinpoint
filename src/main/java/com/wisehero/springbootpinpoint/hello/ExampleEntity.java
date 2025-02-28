package com.wisehero.springbootpinpoint.hello;

import com.wisehero.springbootpinpoint.hello.request.ExampleRequestBody;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private String description;

    @Builder
    private ExampleEntity(String name, Integer age, String description) {
        this.name = name;
        this.age = age;
        this.description = description;
    }

    public static ExampleEntity of(ExampleRequestBody exampleRequestBody) {
        return ExampleEntity.builder()
                .name(exampleRequestBody.name())
                .age(exampleRequestBody.age())
                .description(exampleRequestBody.description())
                .build();
    }

    public void updateEntity(ExampleRequestBody exampleRequestBody) {
        this.name = exampleRequestBody.name();
        this.age = exampleRequestBody.age();
        this.description = exampleRequestBody.description();
    }
}
