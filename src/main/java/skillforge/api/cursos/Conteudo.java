package skillforge.api.cursos;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "conteudo")
@Entity(name = "Conteudo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class Conteudo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "aula_id")
    private Aula aula;

}
