package skillforge.api.dto;

import java.util.List;

public record QuestaoCreateDTO(

        String titulo,
        String enunciado,
        List<String> alternativas,
        String correta,
        Long aulaId


) {
}
