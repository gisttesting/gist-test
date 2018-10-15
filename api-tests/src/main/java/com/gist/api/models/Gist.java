package com.gist.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@Accessors(chain = true)
public class Gist {

    private final Map<String, GistFile> files;

    private String description;

    @JsonProperty("public")
    private boolean isPublic;

}
