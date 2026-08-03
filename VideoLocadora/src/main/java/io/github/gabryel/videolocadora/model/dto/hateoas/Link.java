package io.github.gabryel.videolocadora.model.dto.hateoas;

public record Link(String href, String action, String type) {

    public Link(String href, String action) {
        this(href, action, "application/json");
    }
}
