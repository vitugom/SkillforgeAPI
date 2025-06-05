package skillforge.api.dto;

import java.util.List;

public record CursoDTO(
        Long id,
        String nomeCurso,
        CategoriaDTO categoria,
        String thumbnail
) { }
