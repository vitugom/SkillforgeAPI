package skillforge.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(

        Long id,
        @NotBlank
        String nome,

        String imagem) {
}
