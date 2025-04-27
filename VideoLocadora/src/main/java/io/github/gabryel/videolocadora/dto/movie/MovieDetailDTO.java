package io.github.gabryel.videolocadora.dto.movie;

import io.github.gabryel.videolocadora.enums.MediaGenre;
import io.github.gabryel.videolocadora.enums.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Objeto contendo os detalhes de um filme.")
public record MovieDetailDTO(
        @Schema(description = "ID do filme.", example = "1")
        Long id,

        @Schema(description = "Título do filme.", example = "O Senhor dos Aneis: A Sociedade do Anel", required = true)
        String title,

        @Schema(description = "Classificação indicativa do filme.", example = "Livre", required = true)
        String contentRating,

        @Schema(description = "Código interno do filme.", example = "123456", required = true)
        String internalCode,

        @Schema(description = "Preço do filme.", example = "19.90", required = true)
        Double price,

        @Schema(description = "Indica se o filme est&aacute; dispon&iacute;vel.", example = "true", required = true)
        boolean available,

        @Schema(description = "Tipo do filme.", example = "DVD", required = true)
        MediaType type,

        @Schema(description = "G&ecirc;nero do filme.", example = "Aventura", required = true)
        MediaGenre genre
) {
}
