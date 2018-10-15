package com.gist.api.services;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

public class BaseService {

    private static ResourceBundle rb = ResourceBundle.getBundle("config");

    public RequestSpecification setup() {
        final String GIST_BASE_URI = rb.getString("url");
        final String OAUTH_TOKEN = rb.getString("token");
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(OAUTH_TOKEN)
                .filters(new RequestLoggingFilter(),
                        new ResponseLoggingFilter(),
                        new AllureRestAssured())
                .baseUri(GIST_BASE_URI);
    }
}
