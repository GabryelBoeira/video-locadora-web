package io.github.gabryel.videolocadora.model.mapper.hateoas;

import io.github.gabryel.videolocadora.model.dto.hateoas.Link;

import java.util.HashMap;
import java.util.Map;

public class LinkBuilder {

    private final String baseUrl;
    private final Map<String, Link> links = new HashMap<>();

    private LinkBuilder(String basePath, Object id) {
        this.baseUrl = (id != null) ? basePath + "/" + id : basePath;
    }

    public static LinkBuilder from(String basePath, Object id) {
        return new LinkBuilder(basePath, id);
    }

    public static LinkBuilder from(String basePath) {
        return new LinkBuilder(basePath, null);
    }

    public LinkBuilder self() {
        links.put("self", new Link(baseUrl, "GET"));
        return this;
    }

    public LinkBuilder put() {
        links.put("put", new Link(baseUrl, "PUT"));
        return this;
    }

    public LinkBuilder patch() {
        links.put("patch", new Link(baseUrl, "PATCH"));
        return this;
    }

    public LinkBuilder delete() {
        links.put("delete", new Link(baseUrl, "DELETE"));
        return this;
    }

    public LinkBuilder custom(String rel, String pathSuffix, String action) {
        links.put(rel, new Link(baseUrl + pathSuffix, action));
        return this;
    }

    public LinkBuilder crud() {
        return this.self().patch().delete();
    }

    public LinkBuilder all() {
        return this.self().put().patch().delete();
    }

    public Map<String, Link> build() {
        return links;
    }

}