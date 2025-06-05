package skillforge.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import skillforge.api.cursos.Categoria;
import skillforge.api.dto.CategoriaCreateDTO;
import skillforge.api.dto.CategoriaDTO;
import skillforge.api.dto.CategoriaUpdateDTO;
import skillforge.api.repository.CategoriaRepository;
import skillforge.api.services.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final ObjectMapper objectMapper;

    public CategoriaController(CategoriaService categoriaService, ObjectMapper objectMapper){
        this.categoriaService = categoriaService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> criar(@RequestPart("dados") @Valid String dadosJson, @RequestPart("imagem")MultipartFile imagem)
            throws JsonProcessingException {
        CategoriaCreateDTO dto = objectMapper.readValue(dadosJson, CategoriaCreateDTO.class);
        CategoriaDTO novaCategoria = categoriaService.criar(dto, imagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        return ResponseEntity.ok(categoriaService.listar());
    }

    @PutMapping("/{id}/nome")
    public ResponseEntity<CategoriaDTO> atualizarNome(@PathVariable Long id, @RequestBody CategoriaUpdateDTO dto){

        CategoriaDTO atualizada = categoriaService.atualizarNome(id, dto);
        return ResponseEntity.ok(atualizada);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }



}
