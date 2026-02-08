package io.github.gabryel.videolocadora.model.dto.movie;


import io.github.gabryel.videolocadora.model.enums.ContentRating;
import io.github.gabryel.videolocadora.model.enums.MediaGenre;
import io.github.gabryel.videolocadora.model.enums.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Objeto contendo os detalhes de um filme.")
public record MovieSaveDTO(
        @Schema(description = "Título do filme.", example = "O Senhor dos Aneis: A Sociedade do Anel", required = true)
        @NotBlank
        String title,

        @Schema(description = "Classificação indicativa do filme.", example = "Livre", required = true)
        @NotBlank
        ContentRating contentRating,

        @Schema(description = "Preço do filme.", example = "19.90", required = true)
        @NotNull
        Double price,

        @Schema(description = "Disponibilidade do filme.", example = "true", required = true)
        @NotBlank
        Boolean available,

        @Schema(description = "Tipo do filme.", example = "DVD", required = true)
        @NotBlank
        MediaType type,

        @Schema(description = "Genero do filme.", example = "Aventura", required = true)
        @NotBlank
        MediaGenre genre
) {
}
