package io.github.gabryel.videolocadora.model.dto.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.github.gabryel.videolocadora.model.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.model.mapper.hateoas.LinkBuilder;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public record ResourceCollection<T>(List<ResourceItem<T>> data,
                                    @JsonProperty("_links") Map<String, Link> links,
                                    PaginationMeta meta
) {
    public record ResourceItem<T>(
            @JsonUnwrapped T content,
            @JsonProperty("_links") Map<String, Link> links
    ) {
    }

    public record PaginationMeta(
            @JsonProperty("current_page") int currentPage,
            @JsonProperty("per_page") int perPage,
            @JsonProperty("total_items") long totalItems,
            @JsonProperty("total_pages") int totalPages
    ) {
    }

    public static <T> ResourceCollection<T> of(
            PagedResponseDTO<T> pagedResponse,
            String basePath,
            Function<T, Object> idExtractor
    ) {
        int currentPage = pagedResponse.pageNumber() + 1;
        int limit = pagedResponse.pageSize();
        long total = pagedResponse.totalElements();
        int totalPages = pagedResponse.totalPages() == 0 ? 1 : pagedResponse.totalPages();

        List<ResourceItem<T>> dataWithLinks = pagedResponse.content().stream()
                .map(item -> new ResourceItem<T>(
                        item,
                        LinkBuilder.from(basePath, idExtractor.apply(item)).crud().build()
                ))
                .toList();

        Map<String, Link> links = new LinkedHashMap<>();
        links.put("self", new Link(buildPageUrl(basePath, currentPage, limit), "GET"));
        links.put("first", new Link(buildPageUrl(basePath, 1, limit), "GET"));
        links.put("last", new Link(buildPageUrl(basePath, totalPages, limit), "GET"));

        if (currentPage > 1) {
            links.put("prev", new Link(buildPageUrl(basePath, currentPage - 1, limit), "GET"));
        }

        if (currentPage < totalPages) {
            links.put("next", new Link(buildPageUrl(basePath, currentPage + 1, limit), "GET"));
        }

        PaginationMeta meta = new PaginationMeta(currentPage, limit, total, totalPages);

        return new ResourceCollection<>(dataWithLinks, links, meta);
    }

    private static String buildPageUrl(String basePath, int page, int limit) {
        return basePath + "?page=" + page + "&limit=" + limit;
    }

}
