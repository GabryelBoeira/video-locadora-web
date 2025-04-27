package io.github.gabryel.videolocadora.dto.movie;


import io.github.gabryel.videolocadora.enums.MediaGenre;
import io.github.gabryel.videolocadora.enums.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Objeto contendo os detalhes de um filme.")
public record MovieSaveDTO(
        @Schema(description = "Título do filme.", example = "O Senhor dos Aneis: A Sociedade do Anel", required = true)
        @NotBlank
        String title,

        @Schema(description = "Título do filme.", example = "O Senhor dos Aneis: A Sociedade do Anel", required = true)
        @NotBlank
        String contentRating,

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
