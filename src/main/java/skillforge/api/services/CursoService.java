package skillforge.api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import skillforge.api.cursos.Categoria;
import skillforge.api.cursos.Curso;
import skillforge.api.dto.CategoriaDTO;
import skillforge.api.dto.CursoCreateDTO;
import skillforge.api.dto.CursoDTO;
import skillforge.api.repository.CategoriaRepository;
import skillforge.api.repository.CursoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CategoriaRepository categoriaRepository;

    private final Path pastaImagens = Paths.get("storage/imagens");

    public CursoService(CursoRepository cursoRepository, CategoriaRepository categoriaRepository) {
        this.cursoRepository = cursoRepository;
        this.categoriaRepository = categoriaRepository;

        try {
            Files.createDirectories(pastaImagens);
        } catch (IOException e){
            throw new RuntimeException("Erro ao criar diretorio de imagem");
        }

    }

    public CursoDTO criar(CursoCreateDTO dto, MultipartFile thumbnail) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(()-> new IllegalArgumentException("Categoria não encontrada"));

        String nomeArquivo = UUID.randomUUID() + "_" + thumbnail.getOriginalFilename();
        Path caminho = pastaImagens.resolve(nomeArquivo);

        try {
            Files.copy(thumbnail.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem da thumbnail", e);
        }

        String url = "/imagens/" + nomeArquivo;

        Curso curso = new Curso();
        curso.setNome(dto.nome());
        curso.setCategoria(categoria);
        curso.setThumbnail(url);

        return toDTO(cursoRepository.save(curso));
    }

    public List<CursoDTO> listar(){
        return cursoRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public CursoDTO buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(()-> new NoSuchElementException("Curso não encontrado"));
    }

    public List<CursoDTO> listarPorCategoria(Long idCategoria){
        return cursoRepository.findByCategoriaId(idCategoria).stream()
                .map(this::toDTO)
                .toList();

    }

    public List<CursoDTO> buscarPorNome(String termo) {
        return cursoRepository.findByNomeContainingIgnoreCase(termo).stream()
                .map(this::toDTO)
                .toList();
    }



    public void deletar(Long id) {
        if(!cursoRepository.existsById(id)){
            throw new NoSuchElementException("Categooria não encontrada");
        }
        cursoRepository.deleteById(id);
    }


    private CursoDTO toDTO(Curso c) {
        Categoria categoria = c.getCategoria();
        CategoriaDTO categoriaDTO = new CategoriaDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getImagem()
        );

        return new CursoDTO(
                c.getId(),
                c.getNome(),
                categoriaDTO,
                c.getThumbnail()
        );
    }



}
