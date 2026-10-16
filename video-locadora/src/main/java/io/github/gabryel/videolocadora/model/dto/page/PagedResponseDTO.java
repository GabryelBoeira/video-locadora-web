package io.github.gabryel.videolocadora.model.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Schema(description = "Objeto  contendo informações paginadas de um recurso da API.")
public record PagedResponseDTO<T>(
        @Schema(description = "Conteúdo da resposta generico")
        List<T> content,

        @Schema(description = "Número da pagina", example = "0", defaultValue = "0")
        int pageNumber,

        @Schema(description = "Tamanho da pagina", example = "10", defaultValue = "10")
        int pageSize,

        @Schema(description = "Total de elementos", example = "10")
        long totalElements,

        @Schema(description = "Total de paginas", example = "1")
        int totalPages,

        @Schema(description = "Primeira pagina", example = "true")
        boolean isFirst,

        @Schema(description = "Ultima pagina", example = "true")
        boolean isLast
) {

    /**
     * Builds a {@link PagedResponseDTO} from a Spring {@link Page}, applying the given entity-to-DTO mapper
     * to its content and preserving the real pagination metadata, even when the current page's content is empty
     * (e.g. a page number past the last page).
     *
     * @param page   the source page, possibly null
     * @param mapper function converting each page element to its DTO representation
     * @return a page of DTOs with the same pagination metadata as the source page
     */
    public static <E, D> PagedResponseDTO<D> fromPage(Page<E> page, Function<E, D> mapper) {
        if (page == null) {
            return new PagedResponseDTO<>(new ArrayList<>(), 0, 0, 0, 0, true, true);
        }

        List<D> content = page.getContent().stream()
                .map(mapper)
                .collect(Collectors.toList());

        return new PagedResponseDTO<>(
                content,
                page.getPageable().getPageNumber(),
                page.getPageable().getPageSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
