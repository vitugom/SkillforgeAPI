package skillforge.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skillforge.api.cursos.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {



}
