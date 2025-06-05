package skillforge.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skillforge.api.cursos.Artigo;
import skillforge.api.cursos.Conteudo;
import skillforge.api.cursos.Questao;
import skillforge.api.cursos.Video;
import skillforge.api.dto.ConteudoDTO;
import skillforge.api.repository.ConteudoRepository;

import java.util.List;

@RestController
@RequestMapping("/conteudos")
public class ConteudoController {

    private final ConteudoRepository conteudoRepository;

    public ConteudoController(ConteudoRepository conteudoRepository){
        this.conteudoRepository = conteudoRepository;
    }

    @GetMapping("/aula/{aulaId}")
    public ResponseEntity<List<ConteudoDTO>> listarPorAula(@PathVariable Long aulaId){
        List<Conteudo> conteudos = conteudoRepository.findByAulaId(aulaId);

        List<ConteudoDTO> dtoList = conteudos.stream()
                .map(this::mapearParaDTO)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    private ConteudoDTO mapearParaDTO(Conteudo conteudo){
        String tipo = switch(conteudo){
            case Video v -> "video";
            case Artigo a -> "artigo";
            case Questao q -> "questão";
            default -> "desconhecido";

        };

        return new ConteudoDTO(
                conteudo.getId(),
                conteudo.getTitulo(),
                tipo
        );

    }


}
