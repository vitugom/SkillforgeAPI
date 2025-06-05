package skillforge.api.dto;

import java.util.List;

public record AulaDetalhadaDTO(

        Long id,
        String titulo,
        List<VideoDTO> videos,
        List<ArtigoDTO> artigos,
        List<QuestaoDTO> questoes

) {
}
