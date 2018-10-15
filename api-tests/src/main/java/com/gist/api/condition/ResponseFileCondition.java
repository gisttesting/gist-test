package com.gist.api.condition;

import com.gist.api.models.GistFile;
import io.restassured.response.ValidatableResponse;
import lombok.AllArgsConstructor;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@AllArgsConstructor
public class ResponseFileCondition implements Condition {
    private GistFile file;

    @Override
    public void check(ValidatableResponse response) {
        HashMap<String, HashMap<String, String>> filesFromResponse = response.extract().path("files");
        HashMap<String, String> fileToVerify = filesFromResponse.get(file.getFileName());
        assertThat(fileToVerify.get("content"), is(equalTo(file.getContent())));
        assertThat(fileToVerify.get("filename"), is(equalTo(file.getFileName())));

    }

    @Override
    public String toString() {
        return "response body should contain files with name " + file.getFileName()
                + " and content " + file.getContent();
    }
}
