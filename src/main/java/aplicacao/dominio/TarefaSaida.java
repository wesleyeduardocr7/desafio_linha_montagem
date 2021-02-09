package aplicacao.dominio;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="tarefa_saida")
public class TarefaSaida implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_tarefa",nullable = false)
    private String nomeTarefa;

    @Column(name = "tempo_execucao",nullable = false)
    private Integer tempoDeExecucao;

    @Column(name = "horario_inicio",nullable = false)
    private LocalDateTime dataHoraMinutoDeInicioDaTarefa;

    public TarefaSaida() {
    }

    public TarefaSaida(String nomeTarefa, Integer tempoDeExecucao, LocalDateTime dataHoraMinutoDeInicioDaTarefa) {
        this.id = null;
        this.nomeTarefa = nomeTarefa;
        this.tempoDeExecucao = tempoDeExecucao;
        this.dataHoraMinutoDeInicioDaTarefa = dataHoraMinutoDeInicioDaTarefa;
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

    public LocalDateTime getDataHoraMinutoDeInicioDaTarefa() {
        return dataHoraMinutoDeInicioDaTarefa;
    }
}
