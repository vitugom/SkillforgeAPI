package skillforge.api.services;


import org.springframework.stereotype.Service;
import skillforge.api.cursos.Aula;
import skillforge.api.cursos.Questao;
import skillforge.api.dto.QuestaoCreateDTO;
import skillforge.api.dto.QuestaoDTO;
import skillforge.api.repository.AulaRepository;
import skillforge.api.repository.QuestaoRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuestaoService {

    private final QuestaoRepository questaoRepository;
    private final AulaRepository aulaRepository;

    public QuestaoService(QuestaoRepository questaoRepository, AulaRepository aulaRepository){
        this.questaoRepository = questaoRepository;
        this.aulaRepository = aulaRepository;
    }

    public QuestaoDTO criar(QuestaoCreateDTO dto) {
        Aula aula = aulaRepository.findById(dto.aulaId())
                .orElseThrow(()-> new IllegalArgumentException("Aula não encontrada"));

        Questao questao = new Questao();
        questao.setTitulo(dto.titulo());
        questao.setEnunciado(dto.enunciado());
        questao.setAlternativas(dto.alternativas());
        questao.setCorreta(dto.correta());
        questao.setAula(aula);

        questao = questaoRepository.save(questao);

        return new QuestaoDTO(
                questao.getId(),
                questao.getTitulo(),
                questao.getEnunciado(),
                questao.getAlternativas(),
                questao.getCorreta()
        );
    }

    public List<QuestaoDTO> listarPorAula(Long aulaId) {
        return questaoRepository.findByAulaId(aulaId).stream()
                .map(q -> new QuestaoDTO(
                        q.getId(),
                        q.getTitulo(),
                        q.getEnunciado(),
                        q.getAlternativas(),
                        q.getCorreta()
                ))
                .toList();
    }

    public void deletar(Long id){
        if(!questaoRepository.existsById(id)){
            throw new NoSuchElementException("Video não encontrado");
        }
        questaoRepository.deleteById(id);
    }

}
