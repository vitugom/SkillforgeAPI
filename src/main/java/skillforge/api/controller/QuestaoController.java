package skillforge.api.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillforge.api.dto.QuestaoCreateDTO;
import skillforge.api.dto.QuestaoDTO;
import skillforge.api.services.QuestaoService;

import java.util.List;

@RestController
@RequestMapping("/conteudos/questoes")
public class QuestaoController {

    private final QuestaoService questaoService;

    public QuestaoController(QuestaoService questaoService){
        this.questaoService = questaoService;
    }

    @PostMapping
    public ResponseEntity<QuestaoDTO> criar(@RequestBody @Valid QuestaoCreateDTO dto) {
        QuestaoDTO questao = questaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(questao);
    }

    @GetMapping("/aula/{aulaId}")
    public ResponseEntity<List<QuestaoDTO>> listarPorAula(@PathVariable Long aulaId){
        return ResponseEntity.ok(questaoService.listarPorAula(aulaId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        questaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
