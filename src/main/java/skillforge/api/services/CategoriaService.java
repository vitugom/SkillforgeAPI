package skillforge.api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import skillforge.api.cursos.Categoria;
import skillforge.api.dto.CategoriaCreateDTO;
import skillforge.api.dto.CategoriaDTO;
import skillforge.api.dto.CategoriaUpdateDTO;
import skillforge.api.repository.CategoriaRepository;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;
    private final Path pastaImagens = Paths.get("storage/imagens");

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;

        try {
            Files.createDirectories(pastaImagens);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar diretório de imagem para categorias", e);
        }
    }

    public CategoriaDTO criar(CategoriaCreateDTO dto, MultipartFile imagem) {
        String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
        Path caminho = pastaImagens.resolve(nomeArquivo);

        try {
            Files.copy(imagem.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem da categoria", e);
        }

        String url = "/imagens/" + nomeArquivo;

        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setImagem(url);

        return toDTO(repository.save(categoria));
    }

    public List<CategoriaDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }


    public CategoriaDTO atualizarNome(Long id, CategoriaUpdateDTO dto){
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoria não encontrada"));

        categoria.setNome(dto.nome());
        return toDTO(repository.save(categoria));
    }


    public void deletar(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoria não encontrada"));

        boolean temCursos = categoria.getCursos() != null && !categoria.getCursos().isEmpty();

        if(temCursos) {
            throw new IllegalStateException("Não é possivel excluir uma categoria com cursos cadastrados.");
        }

        repository.deleteById(id);

    }



    private CategoriaDTO toDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getImagem()
        );
    }
}
