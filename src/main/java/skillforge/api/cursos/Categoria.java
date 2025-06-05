package skillforge.api.cursos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import skillforge.api.dto.CategoriaDTO;

import java.util.List;

@Table(name = "categorias")
@Entity(name = "Categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String imagem;

    @OneToMany(mappedBy = "categoria")
    private List<Curso> cursos;

}
