package skillforge.api.dto;

public record ConteudoDTO(
        Long id,
        String titulo,
        String tipo // "video", "artigo", "questao"
) {
}
