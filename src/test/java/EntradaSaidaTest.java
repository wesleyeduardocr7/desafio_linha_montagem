import aplicacao.dominio.TarefaEntrada;
import aplicacao.dominio.TarefaSaida;
import aplicacao.negocio.tarefaEntrada.TarefaEntradaNegocioImpl;
import aplicacao.negocio.tarefaSaida.TarefaSaidaNegocioImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class EntradaSaidaTest {

    private  TarefaEntradaNegocioImpl tarefaEntradaNegocio = new TarefaEntradaNegocioImpl();
    private  TarefaSaidaNegocioImpl tarefaSaidaNegocio = new TarefaSaidaNegocioImpl();

    @Test
    void somaDoTempoDeExecucaoDastarefasDeEntradaDevemSerIguaisAstarefasDeSaida(){

        try {
            List<TarefaEntrada> tarefaEntradas = tarefaEntradaNegocio.recuperar();
            List<TarefaSaida> tarefaSaidas = tarefaSaidaNegocio.recuperar();
            Assertions.assertEquals(somaTempoTarefasDeEntrada(tarefaEntradas),somaTempoTarefasDeSaida(tarefaSaidas));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Integer somaTempoTarefasDeEntrada(List<TarefaEntrada> tarefaEntradas){
        return tarefaEntradas.stream()
                .map(TarefaEntrada::getTempoDeExecucao)
                .reduce(0, Integer::sum);
    }

    private Integer somaTempoTarefasDeSaida(List<TarefaSaida> tarefaSaidas){
        return tarefaSaidas.stream()
                .filter(tarefaSaida -> !tarefaSaida.getNomeTarefa().equals("Almoco"))
                .filter(tarefaSaida -> !tarefaSaida.getNomeTarefa().equals("Ginástica laboral"))
                .map(TarefaSaida::getTempoDeExecucao)
                .reduce(0, Integer::sum);
    }
}
