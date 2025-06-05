package skillforge.api.dto;

public record VideoCreateDTO(

        String titulo,
        String url,
        Integer duracao,
        Long aulaId

) {
}
