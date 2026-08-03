package io.github.gabryel.videolocadora.model.dto.movie;

import io.github.gabryel.videolocadora.model.enums.ContentRating;
import io.github.gabryel.videolocadora.model.enums.MediaGenre;
import io.github.gabryel.videolocadora.model.enums.MediaType;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Objeto contendo os detalhes de um filme.")
public record MovieDetailDTO(
        @Schema(description = "ID do filme.", example = "1")
        Long id,

        @Schema(description = "Título do filme.", example = "O Senhor dos Aneis: A Sociedade do Anel")
        String title,

        @Schema(description = "Classificação indicativa do filme.", example = "Livre")
        ContentRating contentRating,

        @Schema(description = "Código interno do filme.", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID internalCode,

        @Schema(description = "Preço do filme.", example = "19.90")
        Double price,

        @Schema(description = "Indica se o filme est&aacute; dispon&iacute;vel.", example = "true")
        boolean available,

        @Schema(description = "Tipo do filme.", example = "DVD")
        MediaType type,

        @Schema(description = "G&ecirc;nero do filme.", example = "Aventura")
        MediaGenre genre
) {
}
