package skillforge.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skillforge.api.cursos.Questao;

import java.util.List;

@Repository
public interface QuestaoRepository extends JpaRepository<Questao, Long> {

    List<Questao> findByAulaId(Long aulaId);
}
