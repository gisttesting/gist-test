package com.gist.api.condition;

import com.gist.api.models.GistFile;
import org.hamcrest.Matcher;


public class Conditions {

    private Conditions() {}

    public static StatusCodeCondition statusCode(int code) {
        return new StatusCodeCondition(code);
    }

    public static ResponseBodyFieldCondition bodyField(String jsonPath, Matcher matcher) {
        return new ResponseBodyFieldCondition(jsonPath, matcher);
    }

    public static ResponseFileCondition file(GistFile file) {
        return new ResponseFileCondition(file);
    }
}
