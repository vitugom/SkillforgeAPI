package skillforge.api.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillforge.api.cursos.Aula;
import skillforge.api.dto.ArtigoCreateDTO;
import skillforge.api.dto.ArtigoDTO;
import skillforge.api.services.ArtigoService;

import java.util.List;

@RestController
@RequestMapping("/conteudos/artigos")
public class ArtigoController {

    private final ArtigoService artigoService;

    public ArtigoController(ArtigoService artigoService) {
        this.artigoService = artigoService;
    }

    @PostMapping
    public ResponseEntity<ArtigoDTO> criar(@RequestBody @Valid ArtigoCreateDTO dto){
        ArtigoDTO artigoCriado = artigoService.criar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(artigoCriado);
    }

    @GetMapping("/aula/{aulaId}")
    public ResponseEntity<List<ArtigoDTO>> listarPorAula(@PathVariable Long aulaId){
        return ResponseEntity.ok(artigoService.listarPorAula(aulaId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        artigoService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
