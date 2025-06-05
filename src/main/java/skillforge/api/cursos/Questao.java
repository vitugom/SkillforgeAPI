package skillforge.api.cursos;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Questao")
@Table(name = "questoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Questao extends Conteudo{

    private String enunciado;

    @ElementCollection
    @CollectionTable(name = "questao_alternativas", joinColumns = @JoinColumn(name = "questao_id"))
    @Column(name = "alternativas")
    private List<String> alternativas;

    private String correta;

}
