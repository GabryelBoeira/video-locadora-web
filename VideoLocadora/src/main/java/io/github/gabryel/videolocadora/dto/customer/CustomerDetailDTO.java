package io.github.gabryel.videolocadora.dto.customer;

public record CustomerDetailDTO(
        String name,
        String cpf,
        boolean delayDevolution,
        String email
) {
}
