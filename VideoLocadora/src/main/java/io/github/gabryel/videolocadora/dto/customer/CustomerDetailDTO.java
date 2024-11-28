package io.github.gabryel.videolocadora.dto.customer;

public record CustomerDetailDTO(
        Long id,
        String name,
        String cpf,
        boolean delayDevolution,
        String email
) {
}
