package aplicacao.dominio;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tarefa_entrada")
public class TarefaEntrada implements Comparable< TarefaEntrada >, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_tarefa",nullable = false)
    private String nomeTarefa;

    @Column(name = "tempo_execucao",nullable = false)
    private Integer tempoDeExecucao;

    public TarefaEntrada() {
    }

    public TarefaEntrada(String nomeTarefa, Integer tempoDeExecucao) {
        this.id = null;
        this.nomeTarefa = nomeTarefa;
        this.tempoDeExecucao = tempoDeExecucao;
    }

    public Long getId() {
        return id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public Integer getTempoDeExecucao() {
        return tempoDeExecucao;
    }

    @Override
    public int compareTo(TarefaEntrada tarefaEntrada) {
        return this.getTempoDeExecucao().compareTo(tarefaEntrada.getTempoDeExecucao());
    }
}
