package skillforge.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skillforge.api.cursos.Artigo;

import java.util.List;

@Repository
public interface ArtigoRepository extends JpaRepository<Artigo, Long> {

    List<Artigo> findByAulaId(Long aulaId);

}
