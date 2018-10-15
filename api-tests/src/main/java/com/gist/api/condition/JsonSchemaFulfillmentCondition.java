package com.gist.api.condition;

import io.restassured.response.ValidatableResponse;
import lombok.AllArgsConstructor;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@AllArgsConstructor
public class JsonSchemaFulfillmentCondition implements Condition {
    private String pathToJsonSchema;

    @Override
    public void check(ValidatableResponse response) {
        response.assertThat().body(matchesJsonSchemaInClasspath(pathToJsonSchema));
    }
}
