package com.gist.api.services;

import com.gist.api.utils.PropertyUtils;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseService {

    private final String GIST_BASE_URI = "https://api.github.com/gists";
    //private final String OAUTH_TOKEN = PropertyUtils.get("token");
    private final String OAUTH_TOKEN = "903579e3d13cf9108090d6fcbaa1371f7a470f71";

    public RequestSpecification setup() {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(OAUTH_TOKEN)
                .filters(new RequestLoggingFilter(),
                        new ResponseLoggingFilter(),
                        new AllureRestAssured())
                .baseUri(GIST_BASE_URI);
    }
}
