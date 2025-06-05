package skillforge.api.dto;

import java.util.List;

public record QuestaoDTO(

        Long id,
        String titulo,
        String enunciado,
        List<String> alternativas,
        String correta

) {
}
