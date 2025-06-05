package skillforge.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skillforge.api.cursos.Curso;
import skillforge.api.dto.CursoDTO;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByCategoriaId(Long idCategoria);

    List<Curso> findByNomeContainingIgnoreCase(String termo);

}
