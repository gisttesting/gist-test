package com.gist.api.services;

import com.gist.api.assertions.AssertableResponse;
import com.gist.api.exceptions.NoGistsFoundError;
import com.gist.api.models.Gist;
import com.gist.api.models.GistFile;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class GistManager extends BaseService {

    private static final String SPECIFIC_GIST_ACTION_ENDPOINT = "/%s";

    @Step("get gist by id {gistId}")
    public AssertableResponse getGist(String gistId) {
        return new AssertableResponse(getGistById(gistId));
    }

    @Step("Create gist with parameters {gist}")
    public AssertableResponse createGist(Gist gist) {
        return new AssertableResponse(setup().body(gist)
                .when().post());
    }

    @Step("update gist with id {gistId} with data {updatedGist}")
    public AssertableResponse editGist(String gistId, Gist updatedGist) {
        return new AssertableResponse(setup().body(updatedGist)
                .when().patch(String.format(SPECIFIC_GIST_ACTION_ENDPOINT, gistId)));
    }

    @Step("delete gist with id {gistId}")
    public AssertableResponse deleteGist(String gistId) {
        return new AssertableResponse(setup()
                .when().delete(String.format(SPECIFIC_GIST_ACTION_ENDPOINT, gistId)));
    }

    @Step("get gist that already exists")
    public String getExistingGistId() throws NoGistsFoundError {
        int attempts = 3;
        while (attempts > 0) {
            List<String> existingGistIds = setup().when().get().path("id");
            if (!existingGistIds.isEmpty()) {
                return existingGistIds.get(new Random().nextInt(existingGistIds.size()));
            }
        }
        throw new NoGistsFoundError("There are no existing gists for the user. Please create a gist first");
    }

    @Step("get name of the file from gist that already exists")
    public String getExistingGistFilename(String gistId) {
        Map<String, GistFile> files = getGistById(gistId).path("files");
        if (files.isEmpty()) {
            return "";
        }
        return files.keySet().stream().findAny().get();
    }

    private Response getGistById(String gistId) {
        return setup()
                .when().get(String.format(SPECIFIC_GIST_ACTION_ENDPOINT, gistId));
    }

}
