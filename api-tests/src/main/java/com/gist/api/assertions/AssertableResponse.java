package com.gist.api.assertions;

import com.gist.api.condition.Condition;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class AssertableResponse {

    private final Response response;

    @Step("api response should have {condition}")
    public AssertableResponse shouldHave(Condition condition) {
        condition.check(response.then());
        return this;
    }

    @Step("get response data by {jsonPath}")
    public String get(String jsonPath) {
        return response.path(jsonPath);
    }

}
