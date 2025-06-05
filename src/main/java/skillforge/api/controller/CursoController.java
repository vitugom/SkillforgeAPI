package skillforge.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import skillforge.api.dto.CursoCreateDTO;
import skillforge.api.dto.CursoDTO;
import skillforge.api.services.CursoService;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<CursoDTO> criar(
            @RequestPart("dados") String dadosJson,
            @RequestPart("thumbnail") MultipartFile thumbnail
    ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CursoCreateDTO dto = mapper.readValue(dadosJson, CursoCreateDTO.class);
        CursoDTO cursoCriado = cursoService.criar(dto, thumbnail);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoCriado);
    }


    @GetMapping
    public ResponseEntity<List<CursoDTO>> listar() {
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> buscaPorId(@PathVariable Long id) {

        CursoDTO curso = cursoService.buscarPorId(id);
        return ResponseEntity.ok(curso);

    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<CursoDTO>> ListarPorCategoria(@PathVariable Long id){
        return ResponseEntity.ok(cursoService.listarPorCategoria(id));
    }

    @GetMapping("/busca")
    public ResponseEntity<List<CursoDTO>> buscarCursos(@RequestParam String termo){
        return ResponseEntity.ok(cursoService.buscarPorNome(termo));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cursoService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
