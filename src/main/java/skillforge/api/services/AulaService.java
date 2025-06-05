package skillforge.api.services;


import org.springframework.stereotype.Service;
import skillforge.api.cursos.Aula;
import skillforge.api.cursos.Curso;
import skillforge.api.dto.*;
import skillforge.api.repository.*;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AulaService {

    private final AulaRepository aulaRepository;
    private final CursoRepository cursoRepository;
    private final VideoRepository videoRepository;
    private final ArtigoRepository artigoRepository;
    private final QuestaoRepository questaoRepository;

    public AulaService(AulaRepository aulaRepository, CursoRepository cursoRepository, VideoRepository videoRepository, ArtigoRepository artigoRepository, QuestaoRepository questaoRepository){
        this.aulaRepository = aulaRepository;
        this.cursoRepository = cursoRepository;
        this.videoRepository = videoRepository;
        this.artigoRepository = artigoRepository;
        this.questaoRepository = questaoRepository;

    }
    public AulaDTO criar(AulaCreateDTO dto) {
        Curso curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(()-> new IllegalArgumentException("Curso não encontrado"));

        Aula aula = new Aula();
        aula.setTitulo(dto.titulo());
        aula.setCurso(curso);

        aula = aulaRepository.save(aula);

        return toDTO(aula);

    }

    public List<AulaDTO> listarTodas() {
        return aulaRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public List<AulaComCursoDTO> listarPorCurso(Long cursoId){
        return aulaRepository.findByCursoId(cursoId).stream()
                .map(a -> new AulaComCursoDTO(
                        a.getId(),
                        a.getTitulo(),
                        a.getCurso().getNome()
                ))
                .toList();
    }

    public AulaDetalhadaDTO detalhar(Long id){
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Aula não encontrada"));

        List<VideoDTO> videos = videoRepository.findByAulaId(id).stream()
                .map(v -> new VideoDTO(v.getId(), v.getTitulo(), v.getUrl(), v.getDuracao()))
                .toList();

        List<ArtigoDTO> artigos = artigoRepository.findByAulaId(id).stream()
                .map(a -> new ArtigoDTO(a.getId(), a.getTitulo(), a.getTexto()))
                .toList();

        List<QuestaoDTO> questoes = questaoRepository.findByAulaId(id).stream()
                .map(q -> new QuestaoDTO(
                        q.getId(),
                        q.getTitulo(),
                        q.getEnunciado(),
                        q.getAlternativas(),
                        q.getCorreta()
                ))
                .toList();

        return new AulaDetalhadaDTO(aula.getId(), aula.getTitulo(), videos, artigos, questoes);

    }

    public void deletar(Long id) {
        if(!aulaRepository.existsById(id)) {
            throw new NoSuchElementException("Aula não encontrada");
        }
        aulaRepository.deleteById(id);
    }


    private AulaDTO toDTO(Aula aula) {
        return new AulaDTO(aula.getId(), aula.getTitulo());
    }



}
