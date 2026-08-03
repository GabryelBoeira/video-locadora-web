package io.github.gabryel.videolocadora.model.dto.hateoas;

import java.util.Map;

public record Resource<T>(T data, Map<String, Link> links) {

    public static <T> Resource<T> of(T data, Map<String, Link> links) {
        return new Resource<>(data, links);
    }
}