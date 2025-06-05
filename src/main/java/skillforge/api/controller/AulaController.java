package skillforge.api.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillforge.api.dto.AulaComCursoDTO;
import skillforge.api.dto.AulaCreateDTO;
import skillforge.api.dto.AulaDTO;
import skillforge.api.dto.AulaDetalhadaDTO;
import skillforge.api.services.AulaService;

import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService){
        this.aulaService = aulaService;
    }

    @PostMapping
    public ResponseEntity<AulaDTO> criar(@RequestBody @Valid AulaCreateDTO dto) {
        AulaDTO novaAula = aulaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAula);
    }

    @GetMapping
    public ResponseEntity<List<AulaDTO>> listarTodos() {
        return ResponseEntity.ok(aulaService.listarTodas());
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<AulaComCursoDTO>> listarPorCurso(@PathVariable Long cursoId) {
        List<AulaComCursoDTO> aulas = aulaService.listarPorCurso(cursoId);
        return ResponseEntity.ok(aulas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaDetalhadaDTO> detalhar(@PathVariable Long id){
        return ResponseEntity.ok(aulaService.detalhar(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        aulaService.deletar(id);
        return ResponseEntity.noContent().build();
    }




}
