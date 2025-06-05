package skillforge.api.services;

import org.springframework.stereotype.Service;
import skillforge.api.cursos.Artigo;
import skillforge.api.cursos.Aula;
import skillforge.api.dto.ArtigoCreateDTO;
import skillforge.api.dto.ArtigoDTO;
import skillforge.api.repository.ArtigoRepository;
import skillforge.api.repository.AulaRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArtigoService {

    private final ArtigoRepository artigoRepository;
    private final AulaRepository aulaRepository;

    public ArtigoService(ArtigoRepository artigoRepository, AulaRepository aulaRepository){

        this.artigoRepository = artigoRepository;
        this.aulaRepository = aulaRepository;

    }

    public ArtigoDTO criar(ArtigoCreateDTO dto) {

        Aula aula = aulaRepository.findById(dto.aulaId())
                .orElseThrow(()-> new IllegalArgumentException("Aula não encontrada"));

        Artigo artigo = new Artigo();
        artigo.setTitulo(dto.titulo());
        artigo.setTexto(dto.texto());
        artigo.setAula(aula);

        artigo = artigoRepository.save(artigo);
        return new ArtigoDTO(artigo.getId(), artigo.getTitulo(), artigo.getTexto());

    }

    public List<ArtigoDTO> listarPorAula(Long aulaId) {
        return artigoRepository.findByAulaId(aulaId).stream()
                .map(a -> new ArtigoDTO(a.getId(), a.getTitulo(), a.getTexto()))
                .toList();
    }

    public void deletar(Long id){
        if(!artigoRepository.existsById(id)){
            throw new NoSuchElementException("Video não encontrado");
        }
        artigoRepository.deleteById(id);
    }


}
