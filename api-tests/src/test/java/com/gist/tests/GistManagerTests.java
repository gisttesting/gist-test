package com.gist.tests;

import com.gist.api.exceptions.NoGistsFoundError;
import com.gist.api.models.Gist;
import com.gist.api.models.GistFile;
import com.gist.api.services.GistManager;
import com.gist.testData.GistData;
import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.gist.api.condition.Conditions.*;
import static org.hamcrest.Matchers.*;

/**
 * In this class there are several tests that verify CRUD methods to be able to run them independently without
 * having dependencies on data availability. In this way it's possible to figure out the root cause of a failure
 * very easy. Also it's one test that does complete CRUD inside and verifies that created gist is actually deleted
 * in the end.
 *
 * Also it's possible to use handy feature of rest-assured that allows to serialize json to POJO but I don't know what
 * is correct and valid structure of Gist and GistFile. And using approach with serialization we'll loose ability to
 * have good logs in reports. Actually it's responsibility of contract tests to verify that received json is full
 * and no fields are missed and there are no fields that shouldn't be present in json.
 *
 * I don't do any validations for scenarios that can cause different status codes because appearance of different
 * status codes (like 404, 422, etc) is not describer in API documentation and I don't want to make assumptions
 * based on implementation. But it also could be verified if requirements are defined.
 */
public class GistManagerTests {

    private GistManager gistManager = new GistManager();
    private static Faker faker = new Faker();

    @Test
    public void shouldGetSpecificGist() throws NoGistsFoundError {
        String gistId = gistManager.getExistingGistId();
        gistManager.getGist(gistId)
                .shouldHave(statusCode(HttpStatus.SC_OK))
                .shouldHave(bodyField("id", is(equalTo(gistId))));
    }

    @Test
    public void shouldCreateGist() {
        Gist gist = GistData.constructValidGist();
        gistManager.createGist(gist)
                .shouldHave(statusCode(HttpStatus.SC_CREATED))
                .shouldHave(bodyField("description", is(equalTo(gist.getDescription()))))
                .shouldHave(bodyField("public", is(equalTo(gist.isPublic()))))
                .shouldHave(bodyField("files", hasKey(GistData.getFirstFileNameInGist(gist))));
    }

    @Test
    public void shouldEditGistWithNewData() throws NoGistsFoundError {
        // get gist to be updated
        String gistId = gistManager.getExistingGistId();
        String gistFileNameToUpdate = getFileNameToUpdate(gistId);
        // construct updated gist
        String updatedFileContent = faker.artist().name();
        GistFile updatedFile = new GistFile(gistFileNameToUpdate, updatedFileContent);
        Gist updatedGist = GistData.constructUpdatedGist(gistFileNameToUpdate, updatedFile);

        gistManager.editGist(gistId, updatedGist)
                .shouldHave(statusCode(HttpStatus.SC_OK))
                .shouldHave(bodyField("description", is(equalTo(updatedGist.getDescription()))))
                .shouldHave(file(updatedFile));

    }

    @Test
    // File should be removed if the GistFile is null
    public void shouldRemoveFileFromGist() throws NoGistsFoundError {
        // get gist to be updated
        String gistId = gistManager.getExistingGistId();
        String gistFileNameToUpdate = getFileNameToUpdate(gistId);
        // construct gist with empty file
        Gist updatedGist = GistData.constructUpdatedGist(gistFileNameToUpdate, null);

        gistManager.editGist(gistId, updatedGist)
                .shouldHave(statusCode(HttpStatus.SC_OK))
                .shouldHave(bodyField("files", not(hasKey(gistFileNameToUpdate))));
    }

    @Test
    public void shouldDeleteGist() throws NoGistsFoundError {
        gistManager.deleteGist(gistManager.getExistingGistId())
                .shouldHave(statusCode(HttpStatus.SC_NO_CONTENT));
    }

    @Test
    public void gistCRUD() {
        // created gist
        String createdGistId = gistManager.createGist(GistData.constructValidGist())
                .shouldHave(statusCode(HttpStatus.SC_CREATED))
                .get("id");
        // get gist
        gistManager.getGist(createdGistId)
                .shouldHave(statusCode(HttpStatus.SC_OK));
        // update gist
        gistManager.editGist(createdGistId, GistData.constructValidGist())
                .shouldHave(statusCode(HttpStatus.SC_OK));
        // delete gist
        gistManager.deleteGist(createdGistId)
                .shouldHave(statusCode(HttpStatus.SC_NO_CONTENT));
        // check that deleted gist is not accessible
        gistManager.getGist(createdGistId)
                .shouldHave(statusCode(HttpStatus.SC_NOT_FOUND));
    }

    private String getFileNameToUpdate(String gistId) {
        String gistFileNameToUpdate = gistManager.getExistingGistFilename(gistId);
        if (gistFileNameToUpdate.isEmpty()) {
            gistFileNameToUpdate = faker.color().name().concat(".txt");
        }
        return gistFileNameToUpdate;
    }


}
