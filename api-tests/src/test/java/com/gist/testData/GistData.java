package com.gist.testData;

import com.gist.api.models.GistFile;
import com.gist.api.models.Gist;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

public class GistData {

    private GistData() {}

    private static Faker faker = new Faker();

    public static Gist constructValidGist() {
        GistFile gistFile = new GistFile(faker.color().name().concat(".txt"),
                faker.rickAndMorty().quote());
        Map<String, GistFile> fileMap = new HashMap<>();
        fileMap.put(gistFile.getFileName(), gistFile);
        return constructGist(fileMap, faker.chuckNorris().fact(), true);
    }

    public static Gist constructUpdatedGist(String nameOfFileToBeUpdated, GistFile updatedFile) {
        Map<String, GistFile> fileMap = new HashMap<>();
        fileMap.put(nameOfFileToBeUpdated, updatedFile);
        return constructGist(fileMap, faker.yoda().quote(), true);
    }

    public static Gist constructGist(Map<String, GistFile> files, String description, boolean isPublic) {
        return new Gist(files)
                .setDescription(description);
    }

    public static String getFirstFileNameInGist(Gist gist) {
        return gist.getFiles().keySet().stream().findFirst().get().toString();
    }

}
