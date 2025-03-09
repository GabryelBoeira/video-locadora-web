package io.github.gabryel.videolocadora.dto.page;

import java.util.List;

public record PagedResponseDTO<T> (
    List<T> content,
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages,
    boolean isFirst,
    boolean isLast
) {
}
