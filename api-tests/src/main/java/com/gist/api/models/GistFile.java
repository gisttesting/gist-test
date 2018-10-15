package com.gist.api.models;

import lombok.*;
import lombok.experimental.Accessors;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@Accessors(chain = true)
public class GistFile {

    private final String fileName;
    private final String content;
}
