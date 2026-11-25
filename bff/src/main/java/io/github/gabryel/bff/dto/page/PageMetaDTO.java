package io.github.gabryel.bff.dto.page;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PageMetaDTO(
        @JsonProperty("current_page") int currentPage,
        @JsonProperty("per_page") int perPage,
        @JsonProperty("total_items") long totalItems,
        @JsonProperty("total_pages") int totalPages
) {
}
