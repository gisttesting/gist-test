package com.gist.api.condition;

import io.restassured.response.ValidatableResponse;
import lombok.AllArgsConstructor;
import org.hamcrest.Matcher;

@AllArgsConstructor
public class ResponseBodyFieldCondition implements Condition {
    private String jsonPath;
    private Matcher matcher;

    @Override
    public void check(ValidatableResponse response) {
        response.assertThat().body(jsonPath, matcher);
    }

    @Override
    public String toString() {
        return "response body field for jsonPath '" + jsonPath
                + "' that should be " + matcher.toString();
    }
}
